package com.mag.bpm.services.listeners;

import static com.mag.bpm.commons.CreditProcessVariables.DOCUMENT_METADATA_LIST_VARIABLE;
import static com.mag.bpm.commons.CreditProcessVariables.MISSING_DOCUMENTS_RECEIVED_VARIABLE;

import com.mag.bpm.models.documents.DocumentMetadata;
import com.mag.bpm.services.DocumentService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.engine.variable.value.ObjectValue;
import org.springframework.stereotype.Component;

@Component("endMissingDocumentsReceivedListener")
@RequiredArgsConstructor
public class UpdateDocumentServiceTask implements JavaDelegate {

  private final DocumentService documentService;

  @Override
  public void execute(DelegateExecution delegateExecution) throws Exception {
    List<DocumentMetadata> documentMetadataList =
        documentService.getDocumentsByBusinessKey(delegateExecution.getBusinessKey());

    ObjectValue documentMetadataListTyped =
        Variables.objectValue(documentMetadataList)
            .serializationDataFormat(Variables.SerializationDataFormats.JSON)
            .create();
    delegateExecution.setVariable(MISSING_DOCUMENTS_RECEIVED_VARIABLE, true);
    delegateExecution.setVariable(DOCUMENT_METADATA_LIST_VARIABLE, documentMetadataListTyped);
  }
}
