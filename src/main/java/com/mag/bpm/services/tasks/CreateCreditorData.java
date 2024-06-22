package com.mag.bpm.services.tasks;

import com.mag.bpm.models.CreditOffer;
import com.mag.bpm.services.CreditProcessService;
import com.mag.bpm.services.CreditorService;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Component("stCreateCreditorData")
@RequiredArgsConstructor
public class CreateCreditorData  implements JavaDelegate {

  private final CreditProcessService creditProcessService;
  private final CreditorService creditorService;

  @Override
  public void execute(DelegateExecution delegateExecution) throws Exception {
    CreditOffer creditOffer = creditProcessService.getCreditOfferProcessVariable(delegateExecution.getId());
    if (!creditProcessService.getBooleanProcessVariable(delegateExecution, "existingCustomer1")) {
      creditorService.saveCreditorToDb(creditOffer.getCreditor1());
    }
    if (!creditProcessService.getBooleanProcessVariable(delegateExecution, "existingCustomer2")) {
      creditorService.saveCreditorToDb(creditOffer.getCreditor2());
    }
  }
}
