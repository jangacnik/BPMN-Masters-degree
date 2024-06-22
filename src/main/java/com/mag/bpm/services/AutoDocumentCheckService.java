package com.mag.bpm.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mag.bpm.models.documents.DocumentMetadata;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.similarity.JaccardSimilarity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AutoDocumentCheckService {

  private static final Double MIN_JACCARD_DISTANCE = 0.75;
  private final JaccardSimilarity jaccardSimilarity = new JaccardSimilarity();
  private final ObjectMapper objectMapper;

  public boolean autoCheckDocument(
      List<String> propertyList, Object creditData, DocumentMetadata documentMetadata) {
    Map<String, String> creditObj =
        objectMapper.convertValue(creditData, new TypeReference<Map<String, String>>() {});
    for (String property : propertyList) {
      String creditValue = creditObj.get(property);
      String documentValue = documentMetadata.getMetadata().get(property);
      if (StringUtils.isBlank(creditValue) || StringUtils.isBlank(documentValue)) {
        return false;
      }
      if (!documentValue.equalsIgnoreCase(creditValue)
          && isBellowMinJaccardDistance(creditValue, documentValue)) {
        return false;
      }
    }
    return true;
  }

  public boolean isBellowMinJaccardDistance(String s1, String s2) {
    return jaccardSimilarity.apply(s1, s2) < MIN_JACCARD_DISTANCE;
  }
}
