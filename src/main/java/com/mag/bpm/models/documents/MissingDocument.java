package com.mag.bpm.models.documents;

import com.mag.bpm.models.enums.CreditorNumber;
import com.mag.bpm.models.enums.DocumentCode;
import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class MissingDocument implements Serializable {
  List<DocumentCode> documentCodes;
  CreditorNumber creditorNumber;
}
