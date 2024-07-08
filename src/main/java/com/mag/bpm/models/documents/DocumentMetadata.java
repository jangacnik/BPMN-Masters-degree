package com.mag.bpm.models.documents;

import com.mag.bpm.models.enums.DocumentCode;
import com.mag.bpm.models.enums.DocumentOrigin;
import java.io.Serializable;
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
  private String description;
  private DocumentCode documentCode;
  private Map<String, String> metadata;
  private String creationDate;
  private DocumentOrigin origin;
  private String offerId;
  private String check;
}
