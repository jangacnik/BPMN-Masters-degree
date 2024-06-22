package com.mag.bpm.services.tasks;

import com.mag.bpm.models.CreditOffer;
import com.mag.bpm.models.documents.DocumentMetadata;
import com.mag.bpm.services.CreditProcessService;
import com.mag.bpm.services.CreditorService;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("stCheckExistingCreditor")
@RequiredArgsConstructor
public class CheckExistingCreditor implements JavaDelegate {

  private final Logger logger = LoggerFactory.getLogger(CheckExistingCreditor.class);
  private final CreditProcessService creditProcessService;
  private final CreditorService creditorService;
  private final RuntimeService runtimeService;

  @Override
  public void execute(DelegateExecution delegateExecution) throws Exception {
    logger.info(delegateExecution.getProcessInstance().getVariables().toString());
    CreditOffer creditOffer = creditProcessService.getCreditOfferProcessVariable(delegateExecution.getId());
    List<DocumentMetadata> documentMetadataList = creditProcessService.getDocumentMetadataListProcessVariable(
        delegateExecution.getId());
    boolean creditorExists1 = creditorService.checkIfCreditorExists(creditOffer.getCreditor1());
    boolean creditorExists2 = Objects.nonNull(creditOffer.getCreditor2()) &&
        creditorService.checkIfCreditorExists(creditOffer.getCreditor2());
    logger.debug(
        "Saved processVariable creditorExists1 with value {}, processVariable creditorExists2 with value {} to process {}",
        creditorExists1, creditorExists2, delegateExecution.getBusinessKey());
    delegateExecution.setVariable("existingCustomer1", creditorExists1);
    delegateExecution.setVariable("existingCustomer2", creditorExists2);

    List<String> test = new ArrayList<>();
    for (DocumentMetadata documentMetadata : documentMetadataList) {
      test.add(documentMetadata.getDocumentId());
    }
    delegateExecution.setVariable("documentIds", test);
  }
}
