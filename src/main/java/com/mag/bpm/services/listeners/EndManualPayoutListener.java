package com.mag.bpm.services.listeners;

import static com.mag.bpm.commons.CreditProcessVariables.CREDIT_CHECKS_FORM_VARIABLE;
import static com.mag.bpm.commons.CreditProcessVariables.UNCHECKED_DOCUMENT_FORM_VARIABLE;
import static com.mag.bpm.commons.SpinMappingTypes.MAPPING_CREDIT_CHECK_LIST;
import static com.mag.bpm.commons.SpinMappingTypes.MAPPING_DOCUMENTS_LIST;

import com.mag.bpm.models.documents.DocumentMetadata;
import com.mag.bpm.models.forms.ManualPayoutResult;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.spin.Spin;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EndManualPayoutListener implements TaskListener {

  private final RuntimeService runtimeService;

  @Override
  public void notify(DelegateTask delegateTask) {
    List<DocumentMetadata> uncheckedDocuments =
        Spin.JSON(delegateTask.getVariable(UNCHECKED_DOCUMENT_FORM_VARIABLE))
            .mapTo(MAPPING_DOCUMENTS_LIST);

    List<ManualPayoutResult> creditChecks =
        Spin.JSON(delegateTask.getVariable(CREDIT_CHECKS_FORM_VARIABLE))
            .mapTo(MAPPING_CREDIT_CHECK_LIST);

    String approved = delegateTask.getVariable("creditDecision").toString();

    long notValidDocumentCount =
        uncheckedDocuments.stream().filter(docs -> "NOT_VALID".equals(docs.getCheck())).count();
    long notValidCreditCheckCount =
        creditChecks.stream().filter(check -> "NOT_VALID".equals(check.getStatus())).count();

    runtimeService.setVariable(
        delegateTask.getProcessInstanceId(),
        "creditManualDecision",
        ("APPROVED".equals(approved) && notValidDocumentCount == 0 && notValidCreditCheckCount == 0)? "APPROVED" : "DECLINED");
  }
}
