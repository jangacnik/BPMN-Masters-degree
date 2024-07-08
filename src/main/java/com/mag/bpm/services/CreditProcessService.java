package com.mag.bpm.services;

import static com.mag.bpm.commons.CreditProcessVariables.CREDIT_OFFER_VARIABLE;
import static com.mag.bpm.commons.CreditProcessVariables.DOCUMENT_METADATA_LIST_VARIABLE;
import static com.mag.bpm.commons.CreditProcessVariables.MISSING_DOCUMENTS_RECEIVED_VARIABLE;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mag.bpm.dto.CreditRequestDto;
import com.mag.bpm.dto.DocumentMetadataUploadDto;
import com.mag.bpm.models.CreditOffer;
import com.mag.bpm.models.documents.DocumentMetadata;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.engine.variable.value.ObjectValue;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class CreditProcessService {

  private final ObjectMapper objectMapper;
  private static final String MISSING_DOCUMENTS_RECIEVED_MSG = "Message_0d0mg9q";

  private final RuntimeService runtimeService;
  private final CreditService creditService;
  private final DocumentService documentService;

  public void startCreditProcess(CreditRequestDto creditRequestDto) throws JsonProcessingException {
    Map<String, Object> variables = new HashMap<String, Object>();
    String offerId = creditRequestDto.getCreditOffer().getOfferId();
    if (StringUtils.isBlank(offerId)) {
      offerId = UUID.randomUUID().toString();
      creditRequestDto.getCreditOffer().setOfferId(offerId);
    }
    // assign
    for (DocumentMetadata documentMetadata : creditRequestDto.getDocumentMetadataList()) {
      documentMetadata.setOfferId(offerId);
    }

    documentService.saveDocumentsToDb(creditRequestDto.getDocumentMetadataList());
    creditService.saveCreditOfferToDb(creditRequestDto.getCreditOffer());
    runtimeService.startProcessInstanceByKey(
        "pdCreditRequest", creditRequestDto.getCreditOffer().getOfferId(), variables);
  }

  public void sendMissingDocumentsReceivedMessage(DocumentMetadataUploadDto documentDto) {
    ProcessInstance processInstance =
        runtimeService
            .createProcessInstanceQuery()
            .processInstanceBusinessKey(documentDto.getBusinessKey())
            .singleResult();
    if (processInstance != null) {

      String processInstanceId = processInstance.getProcessInstanceId();
      List<DocumentMetadata> documentMetadataList =
          documentService.getDocumentsByBusinessKey(documentDto.getBusinessKey());

      for (DocumentMetadata documentMetadata : documentDto.getDocumentMetadataList()) {
        documentMetadata.setOfferId(documentDto.getBusinessKey());
      }

      documentService.saveDocumentsToDb(documentDto.getDocumentMetadataList());
      documentMetadataList.addAll(documentDto.getDocumentMetadataList());

      ObjectValue documentMetadataListTyped =
          Variables.objectValue(documentMetadataList)
              .serializationDataFormat(Variables.SerializationDataFormats.JAVA)
              .create();
      runtimeService.setVariable(processInstanceId, MISSING_DOCUMENTS_RECEIVED_VARIABLE, true);
      runtimeService.setVariable(
          processInstanceId, DOCUMENT_METADATA_LIST_VARIABLE, documentMetadataListTyped);

      runtimeService
          .createMessageCorrelation(MISSING_DOCUMENTS_RECIEVED_MSG)
          .processInstanceBusinessKey(documentDto.getBusinessKey())
          .correlateWithResult();
    }
  }

  public CreditOffer getCreditOfferProcessVariable(String executionId) {
    return (CreditOffer)
        runtimeService.getVariableTyped(executionId, CREDIT_OFFER_VARIABLE).getValue();
  }

  public List<DocumentMetadata> getDocumentMetadataListProcessVariable(String executionId) {
    return (List<DocumentMetadata>)
        runtimeService.getVariableTyped(executionId, DOCUMENT_METADATA_LIST_VARIABLE).getValue();
  }

  public boolean getBooleanProcessVariable(DelegateExecution delegateExecution, String variableName)
      throws JsonProcessingException {
    return objectMapper.readValue(
        delegateExecution.getVariable(variableName).toString(), Boolean.class);
  }
}
