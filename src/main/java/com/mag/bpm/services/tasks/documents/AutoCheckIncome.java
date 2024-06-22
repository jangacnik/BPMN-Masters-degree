package com.mag.bpm.services.tasks.documents;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("stAutoCheckIncome")
@RequiredArgsConstructor
@Log4j2
public class AutoCheckIncome implements JavaDelegate {

  @Value("#{'${com.mag.bpm.metadata.check.variables.income}'.split(',')}")
  private List<String> checkProperties;

  @Override
  public void execute(DelegateExecution delegateExecution) throws Exception {
    for (String s : checkProperties) {
      log.debug("checked property {}", s);
    }
  }
}
