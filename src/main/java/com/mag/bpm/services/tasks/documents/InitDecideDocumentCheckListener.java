package com.mag.bpm.services.tasks.documents;

import com.mag.bpm.models.documents.DocumentMetadata;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.camunda.spin.Spin;
import org.springframework.stereotype.Component;

@Component("initDecideDocumentCheckListener")
@RequiredArgsConstructor
public class InitDecideDocumentCheckListener implements ExecutionListener {

  @Override
  public void notify(DelegateExecution delegateExecution) throws Exception {
    DocumentMetadata documentMetadata =
        Spin.JSON(delegateExecution.getVariableTyped("document").getValue()).mapTo(DocumentMetadata.class);
    delegateExecution.setVariableLocal(
        "documentCode", documentMetadata.getDocumentCode().getValue());
    String json = delegateExecution.getVariable("checkedDocumentIds").toString();
    if (StringUtils.isNotBlank(json)) {
      List<String> autoCheckedDocument =
          Spin.JSON(json).mapTo("java.util.ArrayList<java.lang.String>");
      delegateExecution.setVariableLocal(
          "alreadyChecked", autoCheckedDocument.contains(documentMetadata.getDocumentId()));
    }
  }
}
