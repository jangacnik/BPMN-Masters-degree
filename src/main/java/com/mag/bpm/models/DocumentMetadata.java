package com.mag.bpm.models;

import com.mag.bpm.models.enums.DocumentOrigin;
import com.mag.bpm.models.enums.DocumentType;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DocumentMetadata {
  private String documentId;
  private String documentCode;
  private DocumentType documentType;
  private Map<String, String> metadata;
  private LocalDateTime creationDate;
  private DocumentOrigin origin;
}
