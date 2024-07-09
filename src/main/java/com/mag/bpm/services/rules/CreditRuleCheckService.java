package com.mag.bpm.services.rules;

import com.mag.bpm.models.documents.DocumentMetadata;
import com.mag.bpm.models.rule.CreditRule;
import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class CreditRuleCheckService {

  public List<CreditRule> checkIdDocument(DocumentMetadata documentMetadata) {
    return Collections.emptyList();
  }

  public List<CreditRule> checkBillingDocument(DocumentMetadata documentMetadata) {
    return Collections.emptyList();
  }

  public List<CreditRule> checkCreditOfferDocument(DocumentMetadata documentMetadata) {
    return Collections.emptyList();
  }

  public List<CreditRule> checkIncomeDocument(DocumentMetadata documentMetadata) {
    return Collections.emptyList();
  }

  public List<CreditRule> checkSlaesDocument(DocumentMetadata documentMetadata) {
    return Collections.emptyList();
  }
}
