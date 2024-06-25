package com.mag.bpm.services.tasks.documents;

import com.mag.bpm.models.documents.DocumentMetadata;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.springframework.stereotype.Component;

@Component("initDecideDocumentCheckListener")
@RequiredArgsConstructor
public class InitDecideDocumentCheckListener implements ExecutionListener {

  @Override
  public void notify(DelegateExecution delegateExecution) throws Exception {
    DocumentMetadata documentMetadata =
        (DocumentMetadata) delegateExecution.getVariableTyped("document").getValue();
    delegateExecution.setVariableLocal(
        "documentCode", documentMetadata.getDocumentCode().getValue());
  }
}
