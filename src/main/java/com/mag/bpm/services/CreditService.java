package com.mag.bpm.services;

import com.mag.bpm.repositories.CreditOfferRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreditService {

  private final CreditOfferRepository creditOfferRepository;

  private final Logger logger = LoggerFactory.getLogger(CreditService.class);
}
