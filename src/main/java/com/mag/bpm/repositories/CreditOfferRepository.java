package com.mag.bpm.repositories;

import com.mag.bpm.models.CreditOffer;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CreditOfferRepository extends MongoRepository<CreditOffer, String> {

}
