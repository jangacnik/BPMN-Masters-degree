package com.mag.bpm.models;

import com.mag.bpm.models.enums.DocumentOrigin;
import com.mag.bpm.models.enums.DocumentType;
import java.time.LocalDateTime;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("documentMetadata")
@AllArgsConstructor
@NoArgsConstructor
public class DocumentMetadata {

  @Id
  private String documentId;
  private String documentCode;
  private DocumentType documentType;
  private Map<String, String> metadata;
  private LocalDateTime creationDate;
  private DocumentOrigin origin;
}
