package com.mag.bpm.models;

import com.mag.bpm.models.enums.OfferType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Offer {
  private OfferType offerType;
  private Double offerAmount;
  private Double offerInterest;
  private Integer duration;
  private LocalDateTime payoutDate;
}
