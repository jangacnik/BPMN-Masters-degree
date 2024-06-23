package com.mag.bpm.services;

import com.mag.bpm.models.CreditOffer;
import com.mag.bpm.repositories.CreditOfferRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreditService {

  private final CreditOfferRepository creditOfferRepository;

  public void saveCreditOfferToDb(CreditOffer creditOffer) {
    creditOfferRepository.save(creditOffer);
  }

  public CreditOffer getCreditOfferByOfferId(String offerId) {
    return creditOfferRepository.findByOfferId(offerId).orElseThrow();
  }
}
