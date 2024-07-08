package com.mag.bpm.dto;

import com.mag.bpm.models.documents.DocumentMetadata;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DocumentMetadataUploadDto {
  private String businessKey;
  private List<DocumentMetadata> documentMetadataList;
}
