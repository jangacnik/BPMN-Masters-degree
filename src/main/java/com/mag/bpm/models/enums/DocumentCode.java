package com.mag.bpm.models.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum DocumentCode {
  PAYSLIP("PSD100"), ID("IDC100"), PASSPORT("IDC200"), CREDIT_OFFER("COD100"), SALES_AGREEMENT(
      "SAD100"), BILL("BLL100"), TERMS("TOS100"), EMPLOYEMENT_CONTRACT("ECD100");


  private final String value;
}
