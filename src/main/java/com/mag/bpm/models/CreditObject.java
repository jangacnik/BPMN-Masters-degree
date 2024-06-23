package com.mag.bpm.models;

import com.mag.bpm.models.enums.ObjectType;
import java.io.Serializable;
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
public class CreditObject implements Serializable {
  private String object;
  private ObjectType objectType;
  private String model;
  private String serial;
  private String brand;
  private Double totalPrice;
  private Double netPrice;
  private Double vat;
  private String purchaseDate;
}
