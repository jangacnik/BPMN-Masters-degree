package com.mag.bpm.models.rule;

import com.mag.bpm.models.enums.DocumentCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class CreditCheck {
  private final String ruleCode;
  private final String description;
  private final DocumentCode documentCode;
  private final String status;
  private String relatedDocument;
}
