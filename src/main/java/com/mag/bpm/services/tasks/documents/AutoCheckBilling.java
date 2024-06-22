package com.mag.bpm.services.tasks.documents;

import static com.mag.bpm.commons.CreditProcessVariables.CREDIT_OFFER_VARIABLE;

import com.mag.bpm.models.CreditOffer;
import com.mag.bpm.models.documents.DocumentMetadata;
import com.mag.bpm.services.AutoDocumentCheckService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("stAutoCheckBilling")
@RequiredArgsConstructor
@Log4j2
public class AutoCheckBilling implements JavaDelegate {

  @Value("#{'${com.mag.bpm.metadata.check.variables.billing}'.split(',')}")
  private List<String> checkProperties;

  private final AutoDocumentCheckService autoDocumentCheckService;

  @Override
  public void execute(DelegateExecution delegateExecution) throws Exception {
    DocumentMetadata documentMetadata =
        (DocumentMetadata) delegateExecution.getVariableTyped("document").getValue();
    CreditOffer creditOffer =
        (CreditOffer) delegateExecution.getVariableTyped(CREDIT_OFFER_VARIABLE).getValue();
    boolean autoChecked =
        autoDocumentCheckService.autoCheckDocument(
            checkProperties, creditOffer.getCreditor1(), documentMetadata);
    log.info(
        "Document with id {} was autoChecked (processId: {}) with result {}",
        documentMetadata.getDocumentId(),
        delegateExecution.getId(),
        autoChecked);

    if (autoChecked) {
      List<String> autoCheckedDocument =
          (List<String>) delegateExecution.getVariableTyped("checkedDocumentIds").getValue();
      autoCheckedDocument.add(documentMetadata.getDocumentId());
      delegateExecution.setVariableLocal("checkedDocumentIds", autoCheckedDocument);
    }
  }
}