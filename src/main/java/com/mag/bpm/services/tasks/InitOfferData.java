package com.mag.bpm.services.tasks;

import static com.mag.bpm.commons.CreditProcessVariables.CHECKED_DOCUMENT_IDS_VARIABLE;
import static com.mag.bpm.commons.CreditProcessVariables.CREDIT_OFFER_VARIABLE;
import static com.mag.bpm.commons.CreditProcessVariables.DOCUMENT_METADATA_LIST_VARIABLE;
import static java.util.Collections.emptyList;

import com.mag.bpm.models.CreditOffer;
import com.mag.bpm.models.documents.DocumentMetadata;
import com.mag.bpm.services.CreditService;
import com.mag.bpm.services.DocumentService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.engine.variable.Variables.SerializationDataFormats;
import org.camunda.bpm.engine.variable.value.ObjectValue;
import org.camunda.spin.Spin;
import org.springframework.stereotype.Component;

@Component("stInitOfferData")
@RequiredArgsConstructor
@Log4j2
public class InitOfferData implements JavaDelegate {

  private final DocumentService documentService;
  private final CreditService creditService;

  @Override
  public void execute(DelegateExecution delegateExecution) throws Exception {
    String offerId = delegateExecution.getProcessBusinessKey();
    CreditOffer creditOffer = creditService.getCreditOfferByOfferId(offerId);
    ObjectValue creditOfferTyped = Variables.objectValue(creditOffer)
        .serializationDataFormat(Variables.SerializationDataFormats.JSON)
        .create();

    List<DocumentMetadata> documentMetadataList = documentService.getDocumentsByBusinessKey(offerId);
    ObjectValue documentMetadataListTyped = Variables.objectValue(documentMetadataList)
        .serializationDataFormat(SerializationDataFormats.JSON)
        .create();

    delegateExecution.setVariable(CREDIT_OFFER_VARIABLE, creditOfferTyped);
    delegateExecution.setVariable(DOCUMENT_METADATA_LIST_VARIABLE, documentMetadataListTyped);
    delegateExecution.setVariable(CHECKED_DOCUMENT_IDS_VARIABLE, Spin.JSON(emptyList()).toString());
  }
}
