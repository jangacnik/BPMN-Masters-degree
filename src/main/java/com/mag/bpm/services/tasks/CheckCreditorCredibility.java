package com.mag.bpm.services.tasks;

import static com.mag.bpm.commons.CreditProcessVariables.IS_CREDITOR_1_CREDIBLE;
import static com.mag.bpm.commons.CreditProcessVariables.IS_CREDITOR_2_CREDIBLE;

import com.mag.bpm.models.CreditOffer;
import com.mag.bpm.models.Creditor;
import com.mag.bpm.services.CreditProcessService;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("stCheckCreditorCredibility")
@RequiredArgsConstructor
public class CheckCreditorCredibility implements JavaDelegate {

  private final CreditProcessService creditProcessService;

  @Value("${com.mag.bpm.credit.min.creditor.net.sum}")
  private Double minCreditorNetSum;

  @Override
  public void execute(DelegateExecution delegateExecution) throws Exception {
    CreditOffer creditOffer =
        creditProcessService.getCreditOfferProcessVariable(delegateExecution.getId());

    Double creditor1NetSum = calculateNetSum(creditOffer.getCreditor1());

    delegateExecution.setVariable(IS_CREDITOR_1_CREDIBLE, creditor1NetSum >= minCreditorNetSum);

    if (creditOffer.getCreditor2() != null) {
      Double creditor2NetSum = calculateNetSum(creditOffer.getCreditor2());
      delegateExecution.setVariable(IS_CREDITOR_2_CREDIBLE, creditor2NetSum >= minCreditorNetSum);
    }
  }

  private Double calculateNetSum(Creditor creditor) {
    return creditor.getIncome()
        + creditor.getExtraIncome()
        - creditor.getLease()
        - creditor.getOutgoings();
  }
}
