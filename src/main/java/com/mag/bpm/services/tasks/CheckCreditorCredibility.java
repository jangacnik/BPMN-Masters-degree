package com.mag.bpm.services.tasks;

import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Component("stCheckCreditorCredibility")
@RequiredArgsConstructor
public class CheckCreditorCredibility implements JavaDelegate {
  private final RuntimeService runtimeService;
  @Override
  public void execute(DelegateExecution delegateExecution) throws Exception {
    runtimeService.suspendProcessInstanceById(delegateExecution.getProcessInstanceId());
  }
}
