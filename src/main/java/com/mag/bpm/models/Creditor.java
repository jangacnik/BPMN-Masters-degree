package com.mag.bpm.models;

import com.mag.bpm.models.enums.LivingSituation;
import com.mag.bpm.models.enums.OccupationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.LocalDateTime;
import org.springframework.data.annotation.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Creditor {
  @Id
  private String id;
  private String name;
  private String firstName;
  private LocalDateTime birthdate;
  private String street;
  private String zip;
  private String city;
  private String country;
  private String occupation;
  private OccupationType occupationType;
  private LivingSituation livingSituation;
  private Double income;
  private Double extraIncome;
  private Double outgoings;
  private Double lease;
}