package com.mag.bpm.commons;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SpinMappingTypes {
  public static final String MAPPING_STRING_LIST = "java.util.ArrayList<java.lang.String>";
  public static final String MAPPING_DOCUMENTS_LIST =
      "java.util.ArrayList<com.mag.bpm.models.documents.DocumentMetadata>";
  public static final String MAPPING_MISSING_DOCUMENTS_LIST =
      "java.util.ArrayList<com.mag.bpm.models.documents.MissingDocument>";
}
