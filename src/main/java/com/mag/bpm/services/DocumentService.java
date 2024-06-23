package com.mag.bpm.services;

import com.mag.bpm.models.documents.DocumentMetadata;
import com.mag.bpm.repositories.DocumentMetadataRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@RequiredArgsConstructor
@Service
public class DocumentService {
  private final DocumentMetadataRepository documentMetadataRepository;

  public void saveDocumentsToDb(List<DocumentMetadata> metadataList) {
    documentMetadataRepository.saveAll(metadataList);
  }

  public List<DocumentMetadata> getDocumentsByOfferId(String offerId) {
     return documentMetadataRepository.findAllByOfferId(offerId).orElseThrow();
  }
}
