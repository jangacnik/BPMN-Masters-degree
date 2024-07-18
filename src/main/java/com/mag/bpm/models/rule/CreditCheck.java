package com.mag.bpm.models.rule;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class CreditCheck implements Serializable {
  private final String description;
  private final String status;
  private String relatedDocument;
}
