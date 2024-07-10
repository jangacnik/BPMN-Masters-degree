package com.mag.bpm.commons;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SpinMappingTypes {
  public static final String MAPPING_STRING_LIST = "java.util.ArrayList<java.lang.String>";
  public static final String MAPPING_DOCUMENT_LIST =
      "java.util.ArrayList<com.mag.bpm.models.documents.DocumentMetadata>";
}
