package com.mag.bpm.commons;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EmailNotificationConstants {

  public static final String MISSING_DOCUMENT_ITEM = "Either one of %s %s";
  public static final String MISSING_DOCUMENT_ITEM_POSTFIX = " for %s";

  public static final String EMAIL_MISSING_DOCUMENTS_LIST_VARIABLE = "missingDocuments";
  public static final String EMAIL_CUSTOMER_VARIABLE = "customer";
  public static final String EMAIL_OFFER_ID_VARIABLE = "offerId";
  public static final String EMAIL_CREDIT_SUM_VARIABLE = "creditSum";
  public static final String TEMPLATE_MISSING_DOCUMENTS = "MissingDocumentsEmail";
  public static final String TEMPLATE_CREDIT_APPROVED = "CreditApprovedEmail";
  public static final String TEMPLATE_CREDIT_DECLINED = "CreditDeclinedEmail";
  public static final String TEMPLATE_CREDIT_DELAYED = "CreditDelayedEmail";


  public static final String SUBJECT_CREDIT_APPROVED = "Your credit request was approved";
  public static final String SUBJECT_CREDIT_DECLINED = "Your credit request was declined";
  public static final String SUBJECT_CREDIT_DELAYED = "Apology for credit request processing delay";
}
