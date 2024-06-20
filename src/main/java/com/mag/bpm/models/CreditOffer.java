package com.mag.bpm.models;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("creditOffer")
@AllArgsConstructor
@NoArgsConstructor
public class CreditOffer {
  @Id
  private String offerId;
  private LocalDateTime creationDate;
  private Creditor creditor1;
  private Creditor creditor2;
  private CreditObject object;
  private Offer offer;
}
