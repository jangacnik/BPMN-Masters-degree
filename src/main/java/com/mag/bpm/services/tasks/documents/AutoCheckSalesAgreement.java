package com.mag.bpm.services.tasks.documents;

import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Component("stAutoCheckSalesAgreement")
@RequiredArgsConstructor
public class AutoCheckSalesAgreement implements JavaDelegate {

  @Override
  public void execute(DelegateExecution delegateExecution) throws Exception {

  }
}
