package com.mag.bpm.services.tasks.documents;

import static com.mag.bpm.commons.CreditProcessVariables.CHECKED_DOCUMENT_IDS_VARIABLE;
import static com.mag.bpm.commons.CreditProcessVariables.CREDIT_OFFER_VARIABLE;
import static com.mag.bpm.commons.CreditProcessVariables.DOCUMENT_VARIABLE;
import static com.mag.bpm.commons.SpinMappingTypes.MAPPING_STRING_LIST;

import com.mag.bpm.models.CreditOffer;
import com.mag.bpm.models.documents.DocumentMetadata;
import com.mag.bpm.services.AutoDocumentCheckService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.spin.Spin;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("stAutoCheckSalesAgreement")
@RequiredArgsConstructor
@Log4j2
public class AutoCheckSalesAgreement implements JavaDelegate {

  @Value("#{'${com.mag.bpm.metadata.check.variables.sales.agreement}'.split(',')}")
  private List<String> checkProperties;

  private final AutoDocumentCheckService autoDocumentCheckService;

  @Override
  public void execute(DelegateExecution delegateExecution) throws Exception {
    DocumentMetadata documentMetadata =
        Spin.JSON(delegateExecution.getVariableTyped(DOCUMENT_VARIABLE).getValue())
            .mapTo(DocumentMetadata.class);
    CreditOffer creditOffer =
        Spin.JSON(delegateExecution.getVariableTyped(CREDIT_OFFER_VARIABLE).getValue())
            .mapTo(CreditOffer.class);
    boolean autoChecked =
        autoDocumentCheckService.autoCheckDocument(
            checkProperties, creditOffer.getObject(), documentMetadata);
    log.info(
        "Document with id {} was autoChecked (processId: {}) with result {}",
        documentMetadata.getDocumentId(),
        delegateExecution.getId(),
        autoChecked);

    if (autoChecked) {
      List<String> autoCheckedDocument =
          Spin.JSON(delegateExecution.getVariableTyped(CHECKED_DOCUMENT_IDS_VARIABLE).getValue())
              .mapTo(MAPPING_STRING_LIST);
      autoCheckedDocument.add(documentMetadata.getDocumentId());
      delegateExecution.setVariable(
          CHECKED_DOCUMENT_IDS_VARIABLE, Spin.JSON(autoCheckedDocument).toString());
    }
  }
}
