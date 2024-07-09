package com.mag.bpm.models.rule;

import java.util.List;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@RequiredArgsConstructor
@Document("creditChecks")
public class CreditOfferChecks {
  @Id private String id;
  private final String offerId;
  private final List<CreditCheck> creditChecks;
}
