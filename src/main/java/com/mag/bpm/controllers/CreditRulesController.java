package com.mag.bpm.controllers;

import com.mag.bpm.models.rule.CreditRule;
import com.mag.bpm.services.rules.CreditRuleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rule")
@RequiredArgsConstructor
@Log4j2
public class CreditRulesController {
  private final CreditRuleService creditRuleService;

  @PostMapping()
  public ResponseEntity<CreditRule> addRule(@RequestBody CreditRule creditRule) {
    return ResponseEntity.ok(creditRuleService.saveOrUpdateRuleToDb(creditRule));
  }

  @DeleteMapping("/{ruleId}")
  public ResponseEntity<CreditRule> removeRule(@PathVariable String ruleId) {
    creditRuleService.deleteRule(ruleId);
    return ResponseEntity.ok(null);
  }
}
