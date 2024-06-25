package com.mag.bpm.services.tasks;

import lombok.extern.log4j.Log4j2;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Component("stRequestMissingDocuments")
@Log4j2
public class RequestMissingDocuments  implements JavaDelegate {

  @Override
  public void execute(DelegateExecution delegateExecution) throws Exception {
    log.warn("SEND MISSING DOCS EMAIL");
  }
}
