package com.mag.bpm.repositories;

import com.mag.bpm.models.enums.DocumentCode;
import com.mag.bpm.models.rule.CreditRule;
import java.util.List;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CreditRuleRepository extends MongoRepository<CreditRule, String> {
  Optional<List<CreditRule>> findCreditRulesByDocumentCode(DocumentCode documentCode);
}
