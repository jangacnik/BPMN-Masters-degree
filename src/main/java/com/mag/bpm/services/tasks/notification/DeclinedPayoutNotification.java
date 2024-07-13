package com.mag.bpm.services.tasks.notification;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Component
public class DeclinedPayoutNotification implements JavaDelegate {

  @Override
  public void execute(DelegateExecution delegateExecution) throws Exception {
    // todo
  }
}
