package com.mag.bpm.services.tasks;

import static com.mag.bpm.commons.CreditProcessVariables.MISSING_DOCUMENTS_LIST_VARIABLE;

import com.mag.bpm.models.CreditOffer;
import com.mag.bpm.models.documents.MissingDocument;
import com.mag.bpm.services.CreditProcessService;
import com.mag.bpm.services.EmailService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Component("stRequestMissingDocuments")
@Log4j2
@RequiredArgsConstructor
public class RequestMissingDocuments implements JavaDelegate {

  private final EmailService emailService;
  private final CreditProcessService creditProcessService;
  private final RuntimeService runtimeService;

  @Override
  public void execute(DelegateExecution delegateExecution) throws Exception {

    CreditOffer creditOffer =
        creditProcessService.getCreditOfferProcessVariable(delegateExecution.getId());

    List<MissingDocument> missingDocumentList =
        (List<MissingDocument>)
            runtimeService
                .getVariableTyped(delegateExecution.getId(), MISSING_DOCUMENTS_LIST_VARIABLE)
                .getValue();

    emailService.sendMissingDocumentsMail(
        creditOffer.getCreditor1().getEmail(),
        "Missing Documents",
        missingDocumentList,
        creditOffer);
  }
}
