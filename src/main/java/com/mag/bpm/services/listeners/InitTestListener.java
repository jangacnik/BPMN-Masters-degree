package com.mag.bpm.services.listeners;

import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.springframework.stereotype.Component;

@Component("initTest")
@RequiredArgsConstructor
public class InitTestListener implements ExecutionListener {
  @Override
  public void notify(DelegateExecution delegateExecution) throws Exception {
    // TODO document why this method is empty
  }
}
