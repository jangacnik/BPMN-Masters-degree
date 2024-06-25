package com.mag.bpm.services.tasks;

import com.mag.bpm.models.CreditOffer;
import com.mag.bpm.models.documents.DocumentMetadata;
import com.mag.bpm.models.documents.MissingDocument;
import com.mag.bpm.models.enums.CreditorNumber;
import com.mag.bpm.models.enums.DocumentCode;
import com.mag.bpm.services.CreditProcessService;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.engine.variable.value.ObjectValue;
import org.springframework.stereotype.Component;

@Component("stCheckMissingDocuments")
@RequiredArgsConstructor
@Log4j2
public class CheckMissingDocuments implements JavaDelegate {
  private final List<DocumentCode> requiredIdDocuments =
      List.of(DocumentCode.ID, DocumentCode.PASSPORT);
  private final List<DocumentCode> requiredObjectDocuments =
      List.of(DocumentCode.BILL, DocumentCode.SALES_AGREEMENT);

  private final List<DocumentCode> requiredCreditorDocuments = List.of(DocumentCode.PAYSLIP);
  private final List<DocumentCode> requiredOtherDocuments = List.of(DocumentCode.CREDIT_OFFER);

  private final CreditProcessService creditProcessService;

  @Override
  public void execute(DelegateExecution delegateExecution) throws Exception {
    List<MissingDocument> missingDocumentList = new ArrayList<>();

    List<DocumentMetadata> documentMetadataList =
        creditProcessService.getDocumentMetadataListProcessVariable(delegateExecution.getId());

    CreditOffer creditOffer =
        creditProcessService.getCreditOfferProcessVariable(delegateExecution.getId());

    // check if ID Documents exists
    if (documentMetadataList.stream()
        .noneMatch(
            documentMetadata ->
                requiredIdDocuments.contains(documentMetadata.getDocumentCode())
                    && documentMetadata.getMetadata().get("creditor").equals("0"))) {
      missingDocumentList.add(new MissingDocument(requiredIdDocuments, CreditorNumber.CREDITOR1));
    }
    // check if ID document of creditor 2 exists
    if (creditOffer.getCreditor2() != null
        && documentMetadataList.stream()
            .noneMatch(
                documentMetadata ->
                    requiredIdDocuments.contains(documentMetadata.getDocumentCode())
                        && documentMetadata.getMetadata().get("creditor").equals("1"))) {
      missingDocumentList.add(new MissingDocument(requiredIdDocuments, CreditorNumber.CREDITOR2));
    }

    if (documentMetadataList.stream()
        .noneMatch(
            documentMetadata ->
                requiredCreditorDocuments.contains(documentMetadata.getDocumentCode())
                    && documentMetadata.getMetadata().get("creditor").equals("0"))) {
      missingDocumentList.add(
          new MissingDocument(requiredCreditorDocuments, CreditorNumber.CREDITOR1));
    }

    if (documentMetadataList.stream()
        .noneMatch(
            documentMetadata ->
                requiredObjectDocuments.contains(documentMetadata.getDocumentCode()))) {
      missingDocumentList.add(new MissingDocument(requiredObjectDocuments, CreditorNumber.ANY));
    }

    if (documentMetadataList.stream()
        .noneMatch(
            documentMetadata ->
                requiredOtherDocuments.contains(documentMetadata.getDocumentCode()))) {
      missingDocumentList.add(new MissingDocument(requiredOtherDocuments, CreditorNumber.ANY));
    }

    Integer missingDocumentsRetry =
        (Integer) delegateExecution.getVariable("missingDocumentsRetry");
    if (missingDocumentsRetry != null) missingDocumentsRetry++;
    else missingDocumentsRetry = 0;
    delegateExecution.setVariable("missingDocumentsRetry", missingDocumentsRetry);
    if (!missingDocumentList.isEmpty()) {
      ObjectValue missingDocumentListTyped =
          Variables.objectValue(missingDocumentList)
              .serializationDataFormat(Variables.SerializationDataFormats.JAVA)
              .create();
      delegateExecution.setVariable("missingDocumentList", missingDocumentListTyped);
      for (MissingDocument miss : missingDocumentList) {
        log.warn(
            "Missing document with code(s) {} for creditor {}",
            miss.getDocumentCodes().toString(),
            miss.getCreditorNumber());
      }
    }
    delegateExecution.setVariable("missingDocuments", !missingDocumentList.isEmpty());
    Map<String, Object> t = delegateExecution.getVariables();

    log.warn(t.toString());
  }
}
