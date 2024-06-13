package com.mag.bpm.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.LocalDateTime;
import org.springframework.data.annotation.Id;

@Data
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
