package com.mag.bpm.services.tasks;

import com.mag.bpm.services.CreditProcessService;
import com.mag.bpm.services.CreditorService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("testTaks")
@RequiredArgsConstructor
public class TestTask implements JavaDelegate {


  private final Logger logger = LoggerFactory.getLogger(TestTask.class);
  private final CreditProcessService creditProcessService;
  private final CreditorService creditorService;

  @Override
  public void execute(DelegateExecution delegateExecution) throws Exception {
    String id = delegateExecution.getVariable("documentId").toString();
    List<String> checked = (List<String>) delegateExecution.getVariable("checkedDocumentIds");
    logger.debug("Check document with Id: {}", id);
    checked.add(id);
    delegateExecution.setVariable("checkedDocumentIds", checked);
  }
}
