package com.mag.bpm.models.rule;

import com.mag.bpm.models.enums.DocumentCode;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@RequiredArgsConstructor
@Document("creditRules")
public class CreditRule {
  @Id private String id;
  private final String ruleCode;
  private final String description;
  private final DocumentCode documentCode;
}
