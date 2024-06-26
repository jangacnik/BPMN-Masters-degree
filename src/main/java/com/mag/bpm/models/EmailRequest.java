package com.mag.bpm.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmailRequest {
  @JsonAlias(value = "to_email")
  private String toEmail;

  private String subject;

  private String templateName;
}
