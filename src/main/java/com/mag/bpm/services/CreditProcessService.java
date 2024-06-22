package com.mag.bpm.services;

import static com.mag.bpm.commons.CreditProcessVariables.CREDIT_OFFER_STRING;
import static com.mag.bpm.commons.CreditProcessVariables.CREDIT_OFFER_VARIABLE;
import static com.mag.bpm.commons.CreditProcessVariables.DOCUMENTS_STRING;
import static com.mag.bpm.commons.CreditProcessVariables.DOCUMENT_METADATA_LIST_VARIABLE;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mag.bpm.dto.CreditRequestDto;
import com.mag.bpm.models.CreditOffer;
import com.mag.bpm.models.documents.DocumentMetadata;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreditProcessService {

  private final ObjectMapper objectMapper;

  private final RuntimeService runtimeService;
  private final Logger logger = LoggerFactory.getLogger(CreditProcessService.class);

  public void startCreditProcess(CreditRequestDto creditRequestDto) throws JsonProcessingException {
    Map<String, Object> variables = new HashMap<String, Object>();
    variables.put("offerId", creditRequestDto.getCreditOffer().getOfferId());
    variables.put(CREDIT_OFFER_STRING,
        objectMapper.writeValueAsString(creditRequestDto.getCreditOffer()));
    variables.put(DOCUMENTS_STRING,
        objectMapper.writeValueAsString(creditRequestDto.getDocumentMetadataList()));
    runtimeService.startProcessInstanceByKey("pdCreditRequest",
        creditRequestDto.getCreditOffer().getOfferId(), variables);
  }

  public void sendMissingDocumentsReceivedMessage(String instanceId) {
    runtimeService
        .createMessageCorrelation("Message_0d0mg9q")
        .processInstanceBusinessKey(instanceId)
        .correlateWithResult();
  }

  public CreditOffer getCreditOfferProcessVariable(String executionId)
      throws JsonProcessingException {
    return (CreditOffer) runtimeService.getVariableTyped(executionId, CREDIT_OFFER_VARIABLE)
        .getValue();
  }

  public List<DocumentMetadata> getDocumentMetadataListProcessVariable(
      String executionId)
      throws JsonProcessingException {
    return (List<DocumentMetadata>) runtimeService.getVariableTyped(executionId, DOCUMENT_METADATA_LIST_VARIABLE)
        .getValue();
  }

  public boolean getBooleanProcessVariable(DelegateExecution delegateExecution, String variableName)
      throws JsonProcessingException {
    return objectMapper.readValue(delegateExecution.getVariable(variableName).toString(),
        Boolean.class);
  }
}
