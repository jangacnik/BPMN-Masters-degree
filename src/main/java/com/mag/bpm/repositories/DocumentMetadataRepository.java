package com.mag.bpm.repositories;

import com.mag.bpm.models.documents.DocumentMetadata;
import java.util.List;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DocumentMetadataRepository extends MongoRepository<DocumentMetadata, String> {
  Optional<List<DocumentMetadata>> findAllByOfferId(String offerId);
}
