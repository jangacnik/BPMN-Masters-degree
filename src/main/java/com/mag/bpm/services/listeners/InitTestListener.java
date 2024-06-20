package com.mag.bpm.services.listeners;

import com.mag.bpm.services.CreditProcessService;
import com.mag.bpm.services.CreditorService;
import java.util.ArrayList;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("initTest")
@RequiredArgsConstructor
public class InitTestListener implements ExecutionListener {

  private final Logger logger = LoggerFactory.getLogger(InitCheckCreditTypeDt.class);
  private final CreditProcessService creditProcessService;
  private final CreditorService creditorService;

  @Override
  public void notify(DelegateExecution delegateExecution) throws Exception {
    delegateExecution.setVariable("checkedDocumentIds", new ArrayList<>());
  }
}
