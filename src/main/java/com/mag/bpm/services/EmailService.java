package com.mag.bpm.services;

import com.mag.bpm.models.CreditOffer;
import com.mag.bpm.models.documents.MissingDocument;
import com.mag.bpm.models.enums.CreditorNumber;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
@RequiredArgsConstructor
@Log4j2
public class EmailService {
  private final JavaMailSender mailSender;
  private final TemplateEngine templateEngine;

  @Value("${spring.mail.username}")
  private String fromMail;

  private static final String MISSING_DOCUMENT_ITEM = "Either one of %s %s";
  private static final String MISSING_DOCUMENT_ITEM_POSTFIX = " for %s";

  private static final String EMAIL_MISSING_DOCUMENTS_LIST_VARIABLE = "missingDocuments";
  private static final String EMAIL_CUSTOMER_VARIABLE = "customer";
  private static final String EMAIL_OFFER_ID_VARIABLE = "offerId";
  private static final String TEMPLATE_MISSING_DOCUMENTS = "MissingDocumentsEmail";

  @Async
  public void sendMissingDocumentsMail(
      String toEmail,
      String subject,
      List<MissingDocument> missingDocumentList,
      CreditOffer creditOffer)
      throws MessagingException {
    MimeMessage mimeMessage = mailSender.createMimeMessage();
    MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);

    mimeMessageHelper.setFrom(fromMail);
    mimeMessageHelper.setTo(toEmail);
    mimeMessageHelper.setSubject(subject);

    Context context = new Context();

    List<String> missingDocumentsListItems = new ArrayList<>();
    for (MissingDocument missingDocument : missingDocumentList) {
      String codes = StringUtils.join(missingDocument.getDocumentCodes(), ", ");
      String postFix = getMissingDocumentsPostFix(creditOffer, missingDocument);
      missingDocumentsListItems.add(String.format(MISSING_DOCUMENT_ITEM, codes, postFix));
    }
    context.setVariable(EMAIL_MISSING_DOCUMENTS_LIST_VARIABLE, missingDocumentsListItems);
    context.setVariable(
        EMAIL_CUSTOMER_VARIABLE,
        creditOffer.getCreditor1().getName() + " " + creditOffer.getCreditor1().getFirstName());
    context.setVariable(EMAIL_OFFER_ID_VARIABLE, creditOffer.getOfferId());
    String processedString = templateEngine.process(TEMPLATE_MISSING_DOCUMENTS, context);

    mimeMessageHelper.setText(processedString, true);

    mailSender.send(mimeMessage);

    log.info(
        "Sent missing documents email for offer {} for the following missing documents {}",
        creditOffer.getOfferId(),
        missingDocumentsListItems.toString());
  }

  private String getMissingDocumentsPostFix(
      CreditOffer creditOffer, MissingDocument missingDocument) {
    String postFix = "";
    if (!missingDocument.getCreditorNumber().equals(CreditorNumber.ANY)) {
      postFix =
          String.format(
              MISSING_DOCUMENT_ITEM_POSTFIX,
              missingDocument.getCreditorNumber().equals(CreditorNumber.CREDITOR1)
                  ? creditOffer.getCreditor1().getName()
                      + " "
                      + creditOffer.getCreditor1().getFirstName()
                  : creditOffer.getCreditor2().getName()
                      + " "
                      + creditOffer.getCreditor2().getFirstName());
    }
    return postFix;
  }
}
