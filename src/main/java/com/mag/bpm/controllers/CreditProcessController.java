package com.mag.bpm.controllers;

import com.mag.bpm.dto.CreditRequestDto;
import com.mag.bpm.dto.DocumentMetadataUploadDto;
import com.mag.bpm.services.CreditProcessService;
import com.mag.bpm.services.DummyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/credit")
@RequiredArgsConstructor
@Log4j2
public class CreditProcessController {

  private final CreditProcessService creditProcessService;
  private final DummyService dummyService;

  @PostMapping
  public ResponseEntity<Void> creditProcessReceived(
      @RequestBody CreditRequestDto creditRequestDto) {
    creditProcessService.startCreditProcess(creditRequestDto);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @GetMapping("/dummy")
  public ResponseEntity<CreditRequestDto> createDummyCreditRequest() {
    return ResponseEntity.ok(dummyService.creatDummyCreditOfferDto(true, true, 0));
  }

  @PostMapping("/upload/docs")
  public ResponseEntity<Void> sendMessage(@RequestBody DocumentMetadataUploadDto documentDto) {
    creditProcessService.sendMissingDocumentsReceivedMessage(documentDto);
    return ResponseEntity.ok(null);
  }
}
