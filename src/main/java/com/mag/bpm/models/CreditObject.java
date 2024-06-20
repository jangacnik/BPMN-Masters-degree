package com.mag.bpm.models;

import com.mag.bpm.models.enums.ObjectType;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreditObject {
  private String object;
  private ObjectType objectType;
  private String model;
  private String serial;
  private String brand;
  private Double totalPrice;
  private Double netPrice;
  private Double vat;
  private LocalDateTime purchaseDate;
}
