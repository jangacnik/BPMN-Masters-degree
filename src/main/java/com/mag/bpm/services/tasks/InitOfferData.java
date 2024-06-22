package com.mag.bpm.services.tasks;

import static com.mag.bpm.commons.CreditProcessVariables.CREDIT_OFFER_STRING;
import static com.mag.bpm.commons.CreditProcessVariables.CREDIT_OFFER_VARIABLE;
import static com.mag.bpm.commons.CreditProcessVariables.DOCUMENTS_STRING;
import static com.mag.bpm.commons.CreditProcessVariables.DOCUMENT_METADATA_LIST_VARIABLE;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mag.bpm.models.CreditOffer;
import com.mag.bpm.models.documents.DocumentMetadata;
import com.mag.bpm.services.CreditProcessService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.engine.variable.value.ObjectValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("stInitOfferData")
@RequiredArgsConstructor
public class InitOfferData implements JavaDelegate {


  private final ObjectMapper objectMapper;

  private final RuntimeService runtimeService;
  private final Logger logger = LoggerFactory.getLogger(CreditProcessService.class);

  @Override
  public void execute(DelegateExecution delegateExecution) throws Exception {
    CreditOffer creditOffer = objectMapper.readValue(
        delegateExecution.getVariable(CREDIT_OFFER_STRING).toString(), CreditOffer.class);

    List<DocumentMetadata> documentMetadataList = objectMapper.readValue(
        delegateExecution.getVariable(DOCUMENTS_STRING).toString(), new TypeReference<>() {
        });
    ObjectValue creditOfferTyped = Variables.objectValue(creditOffer)
        .serializationDataFormat(Variables.SerializationDataFormats.JAVA)
        .create();
    ObjectValue documentMetadataListTyped = Variables.objectValue(documentMetadataList)
        .serializationDataFormat(Variables.SerializationDataFormats.JAVA)
        .create();
    runtimeService.removeVariable(delegateExecution.getId(), CREDIT_OFFER_STRING);
    runtimeService.removeVariable(delegateExecution.getId(), DOCUMENTS_STRING);
    delegateExecution.setVariable(CREDIT_OFFER_VARIABLE, creditOfferTyped);
    delegateExecution.setVariable(DOCUMENT_METADATA_LIST_VARIABLE, documentMetadataListTyped);
  }
}
