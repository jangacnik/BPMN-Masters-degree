package com.mag.bpm.repositories;

import com.mag.bpm.models.DocumentMetadata;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DocumentMetadataRepository extends MongoRepository<DocumentMetadata, String> {

}