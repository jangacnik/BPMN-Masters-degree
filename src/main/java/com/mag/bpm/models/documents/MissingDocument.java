package com.mag.bpm.models.documents;

import com.mag.bpm.models.enums.CreditorNumber;
import com.mag.bpm.models.enums.DocumentCode;
import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MissingDocument implements Serializable {
  private List<DocumentCode> documentCodes;
  private CreditorNumber creditorNumber;
}
