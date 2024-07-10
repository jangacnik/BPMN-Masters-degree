package com.mag.bpm.services.tasks;

import static com.mag.bpm.commons.CreditProcessVariables.EXISTING_CUSTOMER_1_VARIABLE;
import static com.mag.bpm.commons.CreditProcessVariables.EXISTING_CUSTOMER_2_VARIABLE;

import com.mag.bpm.models.CreditOffer;
import com.mag.bpm.services.CreditProcessService;
import com.mag.bpm.services.CreditorService;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("stCheckExistingCreditor")
@RequiredArgsConstructor
public class CheckExistingCreditor implements JavaDelegate {

  private final Logger logger = LoggerFactory.getLogger(CheckExistingCreditor.class);
  private final CreditProcessService creditProcessService;
  private final CreditorService creditorService;

  @Override
  public void execute(DelegateExecution delegateExecution) throws Exception {
    CreditOffer creditOffer =
        creditProcessService.getCreditOfferProcessVariable(delegateExecution.getId());
    boolean creditorExists1 = creditorService.checkIfCreditorExists(creditOffer.getCreditor1());
    boolean creditorExists2 =
        Objects.nonNull(creditOffer.getCreditor2())
            && creditorService.checkIfCreditorExists(creditOffer.getCreditor2());
    logger.debug(
        "Saved processVariable creditorExists1 with value {}, processVariable creditorExists2 with value {} to process {}",
        creditorExists1,
        creditorExists2,
        delegateExecution.getBusinessKey());
    delegateExecution.setVariable(EXISTING_CUSTOMER_1_VARIABLE, creditorExists1);
    delegateExecution.setVariable(EXISTING_CUSTOMER_2_VARIABLE, creditorExists2);
  }
}
