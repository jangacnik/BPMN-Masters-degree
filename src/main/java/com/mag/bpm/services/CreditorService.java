package com.mag.bpm.services;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.ignoreCase;

import com.mag.bpm.models.Creditor;
import com.mag.bpm.repositories.CreditorRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreditorService {

  private final Logger logger = LoggerFactory.getLogger(CreditorService.class);
  private final CreditorRepository creditorRepository;

  public void saveCreditorToDb(Creditor creditor) {
    creditorRepository.save(creditor);
  }

  public boolean checkIfCreditorExists(Creditor creditor) {
    return creditorRepository.exists(createExistingCreditorExample(creditor));
  }

  public Creditor getExistingCreditor(Creditor creditor) {
    return creditorRepository.findOne(createExistingCreditorExample(creditor)).orElseThrow();
  }

  private Example<Creditor> createExistingCreditorExample(Creditor creditor) {
    ExampleMatcher modelMatcher = ExampleMatcher.matching()
        .withIgnorePaths("id")
        .withMatcher("name", ignoreCase())
        .withMatcher("firstName", ignoreCase())
        .withMatcher("birthdate", ignoreCase())
        .withMatcher("birthplace", ignoreCase());
    return Example.of(creditor, modelMatcher);
  }
}
