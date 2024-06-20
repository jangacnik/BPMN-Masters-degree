package com.mag.bpm.repositories;

import com.mag.bpm.models.Creditor;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CreditorRepository extends MongoRepository<Creditor, String> {

}
