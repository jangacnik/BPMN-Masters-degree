package com.mag.bpm.commons;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CreditProcessEventConstants {

  public static final String MISSING_DOCUMENTS_RECIEVED_MSG = "Message_0d0mg9q";

  public static final String MAIL_NOT_FOUND_ERROR = "ebMailNotFound";
  public static final String MAIL_CREDIT_APPROVED_MAIL_ERROR = "ebCreditAprrovedError";
  public static final String MAIL_CREDIT_DECLINED_MAIL_ERROR = "ebCreditDeclinedError";
  public static final String MAIL_CREDIT_DELAYED_MAIL_ERROR = "ebCreditDelayedError";
  public static final String MAIL_NOT_FOUND_ERROR_CODE_VARIABLE = "ebMailNotFoundCode";
  public static final String MAIL_NOT_FOUND_ERROR_MSG_VARIABLE = "ebMailNotFoundMsgVariable";
}
