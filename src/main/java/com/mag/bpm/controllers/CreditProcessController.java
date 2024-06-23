package com.mag.bpm.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mag.bpm.dto.CreditRequestDto;
import com.mag.bpm.services.CreditProcessService;
import com.mag.bpm.services.DummyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@RestController
@RequestMapping("/credit")
@RequiredArgsConstructor
@Log4j2
public class CreditProcessController {

  private final CreditProcessService creditProcessService;
  private final PodamFactory factory = new PodamFactoryImpl();
  private final DummyService dummyService;

  @PostMapping
  public ResponseEntity<Void> creditProcessReceived(@RequestBody CreditRequestDto creditRequestDto)
      throws JsonProcessingException {
    creditProcessService.startCreditProcess(creditRequestDto);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @GetMapping("/dummy")
  public ResponseEntity<CreditRequestDto> createDummyCreditRequest() {
    CreditRequestDto dummyCreditRequestDto = factory.manufacturePojoWithFullData(
        CreditRequestDto.class);

    return ResponseEntity.ok(dummyService.creatDummyCreditOfferDto(true, true, 0));
  }

  @PostMapping("/msg/{id}")
  public ResponseEntity<Void> sendMessage(@PathVariable String id) {
    creditProcessService.sendMissingDocumentsReceivedMessage(id);
    return ResponseEntity.ok(null);
  }
}
