package com.mag.bpm.models;

import com.mag.bpm.models.enums.OfferType;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
