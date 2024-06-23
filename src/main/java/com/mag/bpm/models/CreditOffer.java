package com.mag.bpm.models;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Setter
@Getter
@Document("creditOffer")
@AllArgsConstructor
@NoArgsConstructor
public class CreditOffer implements Serializable {
  @Id
  private String offerId;
  private String creationDate;
  private Creditor creditor1;
  private Creditor creditor2;
  private CreditObject object;
  private Offer offer;
}
