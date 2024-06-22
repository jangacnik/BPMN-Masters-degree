package com.mag.bpm.models.documents;

import com.mag.bpm.models.enums.DocumentOrigin;
import com.mag.bpm.models.enums.DocumentCode;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Setter
@Getter
@Document("documentMetadata")
@AllArgsConstructor
@NoArgsConstructor
public class DocumentMetadata implements Serializable {
  @Id
  private String documentId;
  private DocumentCode documentCode;
  private Map<String, String> metadata;
  private LocalDateTime creationDate;
  private DocumentOrigin origin;
}