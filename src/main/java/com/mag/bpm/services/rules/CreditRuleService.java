package com.mag.bpm.services.rules;

import com.mag.bpm.models.rule.CreditRule;
import com.mag.bpm.repositories.CreditRuleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreditRuleService {
  private final CreditRuleRepository creditRuleRepository;


  public CreditRule saveOrUpdateRuleToDb(CreditRule creditRule) {
    return creditRuleRepository.save(creditRule);
  }

  public void deleteRule(String ruleId) {
    creditRuleRepository.deleteById(ruleId);
  }
}
