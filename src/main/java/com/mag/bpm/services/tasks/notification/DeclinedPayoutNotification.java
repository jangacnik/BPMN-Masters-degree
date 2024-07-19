package com.mag.bpm.services.tasks.notification;

import static com.mag.bpm.commons.CreditProcessEventConstants.MAIL_CREDIT_DECLINED_MAIL_ERROR;
import static com.mag.bpm.commons.EmailNotificationConstants.SUBJECT_CREDIT_DECLINED;
import static com.mag.bpm.commons.EmailNotificationConstants.TEMPLATE_CREDIT_DECLINED;

import com.mag.bpm.models.CreditOffer;
import com.mag.bpm.services.CreditProcessService;
import com.mag.bpm.services.EmailService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Log4j2
@Component
@RequiredArgsConstructor
public class DeclinedPayoutNotification implements JavaDelegate {

  private final EmailService emailService;
  private final CreditProcessService creditProcessService;

  @Override
  public void execute(DelegateExecution delegateExecution) throws Exception {
    CreditOffer creditOffer =
        creditProcessService.getCreditOfferProcessVariable(delegateExecution.getId());
    try {
      emailService.sendCreditOfferStatusMail(
          SUBJECT_CREDIT_DECLINED, creditOffer, TEMPLATE_CREDIT_DECLINED);
    } catch (MessagingException mailException) {
      log.error(mailException.getMessage());
      throw new BpmnError(MAIL_CREDIT_DECLINED_MAIL_ERROR);
    }
  }
}
