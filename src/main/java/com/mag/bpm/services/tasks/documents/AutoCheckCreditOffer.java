package com.mag.bpm.services.tasks.documents;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("stAutoCheckCreditOffer")
@RequiredArgsConstructor
public class AutoCheckCreditOffer  implements JavaDelegate {

  @Value("#{'${com.mag.bpm.metadata.check.variables.credit.offer}'.split(',')}")
  private List<String> checkProperties;

  @Override
  public void execute(DelegateExecution delegateExecution) throws Exception {

  }
}
