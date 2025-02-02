package com.mag.bpm.commons;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CreditProcessVariables {
  public static final  String CREDIT_OFFER_VARIABLE = "creditOffer";
  public static final  String DOCUMENT_METADATA_LIST_VARIABLE = "documentMetadataList";
  public static final  String MISSING_DOCUMENTS_RECEIVED_VARIABLE = "missingDocumentReceived";
  public static final  String MISSING_DOCUMENTS_VARIABLE = "missingDocuments";
  public static final  String MISSING_DOCUMENTS_LIST_VARIABLE = "missingDocumentList";
  public static final String MISSING_DOCUMENTS_RETRY_VARIABLE = "missingDocumentsRetry";
  public static final String CHECKED_DOCUMENT_IDS_VARIABLE = "checkedDocumentIds";
  public static final String DOCUMENT_VARIABLE = "document";
  public static final String DOCUMENT_CODE_VARIABLE = "documentCode";
  public static final String DOCUMENT_ALREADY_CHECKED = "alreadyChecked";
  public static final String EXISTING_CUSTOMER_1_VARIABLE =  "existingCustomer1";
  public static final String EXISTING_CUSTOMER_2_VARIABLE =  "existingCustomer2";
  public static final String ARE_CREDITORS_CREDIBLE = "creditorsCredible";
  public static final String AUTO_CHECKED_DOCUMENT_FORM_VARIABLE = "autoCheckedDocumentsForm";
  public static final String UNCHECKED_DOCUMENT_FORM_VARIABLE = "uncheckedDocumentsForm";
  public static final String CREDIT_CHECKS_FORM_VARIABLE = "creditChecks";
}
