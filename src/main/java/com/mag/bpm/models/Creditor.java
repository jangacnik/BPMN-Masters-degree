package com.mag.bpm.models;

import com.mag.bpm.models.enums.LivingSituation;
import com.mag.bpm.models.enums.OccupationType;
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
@Document("creditors")
@AllArgsConstructor
@NoArgsConstructor
public class Creditor implements Serializable {

  @Id
  private String id;
  private String name;
  private String firstName;
  private String sex;
  private String dateOfBirth;
  private String birthplace;
  private String street;
  private String zip;
  private String city;
  private String country;
  private String occupation;
  private String email;
  private String phoneNumber;
  private String nationalId;
  private OccupationType occupationType;
  private LivingSituation livingSituation;
  private Double income;
  private Double extraIncome;
  private Double outgoings;
  private Double lease;
}
