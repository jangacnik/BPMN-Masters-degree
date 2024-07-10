package com.mag.bpm.services.tasks;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Component
public class DefineCreditChecks implements JavaDelegate {

  @Override
  public void execute(DelegateExecution delegateExecution) throws Exception {
    // TODO document why this method is empty
  }
}
