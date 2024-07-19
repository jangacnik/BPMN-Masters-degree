package com.mag.bpm.services.tasks;

import static com.mag.bpm.commons.CreditProcessEventConstants.MAIL_NOT_FOUND_ERROR;
import static com.mag.bpm.commons.CreditProcessVariables.MISSING_DOCUMENTS_LIST_VARIABLE;
import static com.mag.bpm.commons.SpinMappingTypes.MAPPING_MISSING_DOCUMENTS_LIST;

import com.mag.bpm.models.CreditOffer;
import com.mag.bpm.models.documents.MissingDocument;
import com.mag.bpm.services.CreditProcessService;
import com.mag.bpm.services.EmailService;
import jakarta.mail.MessagingException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.spin.Spin;
import org.springframework.stereotype.Component;

@Component("stRequestMissingDocuments")
@Log4j2
@RequiredArgsConstructor
public class RequestMissingDocuments implements JavaDelegate {

  private final EmailService emailService;
  private final CreditProcessService creditProcessService;

  @Override
  public void execute(DelegateExecution delegateExecution) throws Exception {

    CreditOffer creditOffer =
        creditProcessService.getCreditOfferProcessVariable(delegateExecution.getId());

    List<MissingDocument> missingDocumentList =
        Spin.JSON(delegateExecution.getVariable(MISSING_DOCUMENTS_LIST_VARIABLE))
            .mapTo(MAPPING_MISSING_DOCUMENTS_LIST);

    try {
      emailService.sendMissingDocumentsMail(
          creditOffer.getCreditor1().getEmail(),
          "Missing Documents",
          missingDocumentList,
          creditOffer);
    } catch (MessagingException mailException) {
      log.error(mailException.getMessage());
      throw new BpmnError(MAIL_NOT_FOUND_ERROR);
    }
  }
}
