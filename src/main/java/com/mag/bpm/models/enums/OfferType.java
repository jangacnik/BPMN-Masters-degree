package com.mag.bpm.models.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OfferType {
  AUTOMOTIVE("AUTOMOTIVE"), QUICK("QUICK"), ONLINE("ONLINE");

  private final String value;
}
