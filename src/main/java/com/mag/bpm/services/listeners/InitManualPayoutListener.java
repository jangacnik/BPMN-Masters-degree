package com.mag.bpm.services.listeners;

import static com.mag.bpm.commons.CreditProcessVariables.AUTO_CHECKED_DOCUMENT_FORM_VARIABLE;
import static com.mag.bpm.commons.CreditProcessVariables.CHECKED_DOCUMENT_IDS_VARIABLE;
import static com.mag.bpm.commons.CreditProcessVariables.CREDIT_CHECKS_FORM_VARIABLE;
import static com.mag.bpm.commons.CreditProcessVariables.UNCHECKED_DOCUMENT_FORM_VARIABLE;
import static com.mag.bpm.commons.SpinMappingTypes.MAPPING_STRING_LIST;

import com.mag.bpm.models.documents.DocumentMetadata;
import com.mag.bpm.models.rule.CreditCheck;
import com.mag.bpm.services.CreditProcessService;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.spin.Spin;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InitManualPayoutListener implements TaskListener {

  private final CreditProcessService creditProcessService;

  @Value("#{'${com.mag.bpm.credit.check.texts.id}'.split(',')}")
  private List<String> creditChecksTextsId;

  @Value("#{'${com.mag.bpm.credit.check.texts.income}'.split(',')}")
  private List<String> creditChecksTextsIncome;

  @Value("#{'${com.mag.bpm.credit.check.texts.sales.agreement}'.split(',')}")
  private List<String> creditChecksTextsSalesAgreement;

  @Value("#{'${com.mag.bpm.credit.check.texts.credit.offer}'.split(',')}")
  private List<String> creditChecksTextsCreditOffer;

  @Value("#{'${com.mag.bpm.credit.check.texts.billing}'.split(',')}")
  private List<String> creditChecksTextsBilling;

  @Value("#{'${com.mag.bpm.credit.check.texts.default}'.split(',')}")
  private List<String> creditChecksTextsDefault;

  private static final String CREDIT_CHECK_DOCUMENT_TEXT_FORMAT = "%s (For document: %s)";

  private static final String UNCHECKED_STATUS = "UNCHECKED";

  @Override
  public void notify(DelegateTask delegateTask) {
    final List<DocumentMetadata> documentMetadataList =
        creditProcessService.getDocumentMetadataListProcessVariable(
            delegateTask.getProcessInstanceId());
    final List<String> autoCheckedDocumentList =
        Spin.JSON(delegateTask.getVariable(CHECKED_DOCUMENT_IDS_VARIABLE))
            .mapTo(MAPPING_STRING_LIST);

    List<DocumentMetadata> uncheckedDocuments =
        documentMetadataList.stream()
            .filter(docs -> autoCheckedDocumentList.contains(docs.getDocumentId()))
            .toList();

    List<DocumentMetadata> checkedDocuments =
        documentMetadataList.stream()
            .filter(docs -> !autoCheckedDocumentList.contains(docs.getDocumentId()))
            .toList();

    delegateTask.setVariable(AUTO_CHECKED_DOCUMENT_FORM_VARIABLE, Spin.JSON(checkedDocuments));

    List<CreditCheck> creditChecks = new ArrayList<>();
    for (DocumentMetadata documentMetadata : uncheckedDocuments) {
      documentMetadata.setCheck(UNCHECKED_STATUS);
      switch (documentMetadata.getDocumentCode()) {
        case CREDIT_OFFER ->
            creditChecks.addAll(
                createCreditChecks(creditChecksTextsCreditOffer, documentMetadata.getDocumentId()));
        case ID, PASSPORT ->
            creditChecks.addAll(
                createCreditChecks(creditChecksTextsId, documentMetadata.getDocumentId()));
        case PAYSLIP ->
            creditChecks.addAll(
                createCreditChecks(creditChecksTextsIncome, documentMetadata.getDocumentId()));
        case BILL ->
            creditChecks.addAll(
                createCreditChecks(creditChecksTextsBilling, documentMetadata.getDocumentId()));
        case SALES_AGREEMENT ->
            creditChecks.addAll(
                createCreditChecks(
                    creditChecksTextsSalesAgreement, documentMetadata.getDocumentId()));
        default -> {
          // add nothing
        }
      }
    }
    delegateTask.setVariable(UNCHECKED_DOCUMENT_FORM_VARIABLE, Spin.JSON(uncheckedDocuments));

    if (creditChecks.isEmpty()) {
      for (String txt : creditChecksTextsDefault) {
        creditChecks.add(new CreditCheck(txt, UNCHECKED_STATUS));
      }
    }
    delegateTask.setVariable(CREDIT_CHECKS_FORM_VARIABLE, Spin.JSON(creditChecks));
  }

  private List<CreditCheck> createCreditChecks(List<String> texts, String documentId) {
    List<CreditCheck> creditChecks = new ArrayList<>();
    for (String txt : texts) {
      creditChecks.add(
          new CreditCheck(
              String.format(CREDIT_CHECK_DOCUMENT_TEXT_FORMAT, txt, documentId),
              UNCHECKED_STATUS,
              documentId));
    }
    return creditChecks;
  }
}
