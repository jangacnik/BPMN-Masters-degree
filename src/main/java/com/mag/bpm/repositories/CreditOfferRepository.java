package com.mag.bpm.repositories;

import com.mag.bpm.models.CreditOffer;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CreditOfferRepository extends MongoRepository<CreditOffer, String> {
  Optional<CreditOffer> findByOfferId(String offerId);
}
