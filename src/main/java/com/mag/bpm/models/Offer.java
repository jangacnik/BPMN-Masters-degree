package com.mag.bpm.models;

import com.mag.bpm.models.enums.OfferType;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Offer implements Serializable {
  private OfferType offerType;
  private Double offerAmount;
  private Double offerInterest;
  private Integer duration;
  private LocalDateTime payoutDate;
}
