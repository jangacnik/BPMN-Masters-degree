package com.mag.bpm.services.tasks.documents;

import static com.mag.bpm.commons.CreditProcessVariables.CHECKED_DOCUMENT_IDS_VARIABLE;
import static com.mag.bpm.commons.CreditProcessVariables.DOCUMENT_ALREADY_CHECKED;
import static com.mag.bpm.commons.CreditProcessVariables.DOCUMENT_CODE_VARIABLE;
import static com.mag.bpm.commons.CreditProcessVariables.DOCUMENT_VARIABLE;
import static com.mag.bpm.commons.SpinMappingTypes.MAPPING_STRING_LIST;

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
        Spin.JSON(delegateExecution.getVariableTyped(DOCUMENT_VARIABLE).getValue()).mapTo(DocumentMetadata.class);
    delegateExecution.setVariableLocal(
        DOCUMENT_CODE_VARIABLE, documentMetadata.getDocumentCode().getValue());
    String json = delegateExecution.getVariable(CHECKED_DOCUMENT_IDS_VARIABLE).toString();
    if (StringUtils.isNotBlank(json)) {
      List<String> autoCheckedDocument =
          Spin.JSON(json).mapTo(MAPPING_STRING_LIST);
      delegateExecution.setVariableLocal(
          DOCUMENT_ALREADY_CHECKED, autoCheckedDocument.contains(documentMetadata.getDocumentId()));
    }
  }
}
