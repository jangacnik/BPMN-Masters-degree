package com.mag.bpm.models.forms;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ManualPayoutResult implements Serializable {
  private String description;
  private String documentId;
  private String documentCode;
  private String status;
}
