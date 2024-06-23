package com.mag.bpm.services;

import com.devskiller.jfairy.Fairy;
import com.devskiller.jfairy.producer.person.Person;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mag.bpm.dto.CreditRequestDto;
import com.mag.bpm.models.CreditObject;
import com.mag.bpm.models.CreditOffer;
import com.mag.bpm.models.Creditor;
import com.mag.bpm.models.Offer;
import com.mag.bpm.models.documents.DocumentMetadata;
import com.mag.bpm.models.enums.DocumentCode;
import com.mag.bpm.models.enums.DocumentOrigin;
import com.mag.bpm.models.enums.LivingSituation;
import com.mag.bpm.models.enums.ObjectType;
import com.mag.bpm.models.enums.OccupationType;
import com.mag.bpm.models.enums.OfferType;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class DummyService {
  @Value("#{'${com.mag.bpm.metadata.check.variables.credit.offer}'.split(',')}")
  private List<String> checkPropertiesCreditOffer;

  @Value("#{'${com.mag.bpm.metadata.check.variables.billing}'.split(',')}")
  private List<String> checkPropertiesBilling;

  @Value("#{'${com.mag.bpm.metadata.check.variables.id}'.split(',')}")
  private List<String> checkPropertiesId;

  @Value("#{'${com.mag.bpm.metadata.check.variables.income}'.split(',')}")
  private List<String> checkPropertiesIncome;

  @Value("#{'${com.mag.bpm.metadata.check.variables.sales.agreement}'.split(',')}")
  private List<String> checkPropertiesSalesAgreement;

  private static final List<String> countries =
      List.of("Slovenia", "Austria", "Germany", "Italy", "Croatia", "Norway");

  private static final List<String> occupations =
      List.of("Artist", "Programmer", "Farmer", "Sales Manager", "Office worker", "Chef");

  private static final List<String> brands =
      List.of("Tesla", "BMW", "Skoda", "Toyota", "Samsung", "Apple", "Xiaomi", "Google", "LG");
  private static final List<String> objects =
      List.of(
          "Smartphone", "Car", "Fridge", "Appliance", "Smartwatch", "Table", "Computer", "Laptop");

  private static final Double INCOME_MIN = 1200.0;
  private static final Double INCOME_MAX = 4200.0;

  private static final Double EXTRA_INCOME_MIN = 0.0;
  private static final Double EXTRA_INCOME_MAX = 1200.0;

  private static final Double TOTAL_PRICE_MIN = 599.99;
  private static final Double TOTAL_PRICE_MAX = 100000.0;

  private static final Double VAT_MIN = 0.07;
  private static final Double VAT_MAX = 0.22;

  private static final Double INTEREST_MIN = 0.03;
  private static final Double INTEREST_MAX = 0.09;

  private static final Integer DURATION_MIN = 36;
  private static final Integer DURATION_MAX = 240;
  private static final Integer REQUESTED_PAYOUT_DATE_MIN = 7;
  private static final Integer REQUESTED_PAYOUT_DATE_MAX = 31;
  private List<OccupationType> occupationTypes;
  private List<LivingSituation> livingSituations;
  private List<ObjectType> objectTypes;
  private List<DocumentCode> documentCodes;
  private List<OfferType> offerTypes;
  private List<DocumentOrigin> documentOrigins;
  private final ObjectMapper objectMapper;

  DummyService() {
    //    fairy = Fairy.create();
    occupationTypes = Arrays.asList(OccupationType.values());
    livingSituations = Arrays.asList(LivingSituation.values());
    objectTypes = Arrays.asList(ObjectType.values());
    documentCodes = Arrays.asList(DocumentCode.values());
    offerTypes = Arrays.asList(OfferType.values());
    objectMapper = new ObjectMapper();
    documentOrigins = Arrays.asList(DocumentOrigin.values());

    objectMapper.registerModule(new JavaTimeModule());
  }

  public CreditRequestDto creatDummyCreditOfferDto(
      boolean createCreditor2, boolean completeDocumentsList, int numberOfDocuments) {
    CreditRequestDto creditRequestDto = new CreditRequestDto();
    CreditOffer creditOffer = new CreditOffer();
    String offerId = createOfferId();
    creditOffer.setOfferId(offerId);
    creditOffer.setCreationDate(LocalDateTime.now().toString());
    creditOffer.setCreditor1(createDummyCreditor());
    if (createCreditor2) {
      creditOffer.setCreditor2(createDummyCreditor());
    }
    creditOffer.setObject(createCreditObject());
    creditOffer.setOffer(createCreditOffer(creditOffer.getObject()));
    creditRequestDto.setCreditOffer(creditOffer);

    List<DocumentMetadata> documentMetadataList = new ArrayList<>();
    if (completeDocumentsList) {
      createDocumentsFull(documentMetadataList, creditOffer);
    } else {
      createDocumentsRandom(documentMetadataList, creditOffer, numberOfDocuments);
    }
    creditRequestDto.setDocumentMetadataList(documentMetadataList);
    return creditRequestDto;
  }

  private void createDocumentsFull(
      List<DocumentMetadata> documentMetadataList, CreditOffer creditOffer) {
    for (DocumentCode documentCode : documentCodes) {
      documentMetadataList.add(createDocument(documentCode, creditOffer));
    }
  }

  private void createDocumentsRandom(
      List<DocumentMetadata> documentMetadataList, CreditOffer creditOffer, int numberOfDocuments) {
    Fairy fairy = Fairy.create();
    for (int i = 0; i < numberOfDocuments; i++) {
      documentMetadataList.add(
          createDocument(fairy.baseProducer().randomElement(documentCodes), creditOffer));
    }
  }

  private DocumentMetadata createDocument(DocumentCode documentCode, CreditOffer creditOffer) {
    Fairy fairy = Fairy.create();
    List<String> properties = Collections.emptyList();
    Object creditData = null;
    switch (documentCode) {
      case ID:
        properties = checkPropertiesId;
        creditData = creditOffer.getCreditor1();
        break;
      case PASSPORT:
        properties = checkPropertiesId;
        creditData = creditOffer.getCreditor1();
        break;
      case BILL:
        properties = checkPropertiesBilling;
        creditData = creditOffer.getObject();
        break;
      case PAYSLIP:
        properties = checkPropertiesIncome;
        creditData = creditOffer.getCreditor1();
        break;
      case SALES_AGREEMENT:
        properties = checkPropertiesSalesAgreement;
        creditData = creditOffer.getObject();
        break;
      case CREDIT_OFFER:
        properties = checkPropertiesCreditOffer;
        creditData = creditOffer.getOffer();
        break;
      default:
        break;
    }

    long daysSinceCreation = fairy.baseProducer().randomBetween(0, 100);
    return new DocumentMetadata(
        createRandomUuidString(),
        documentCode,
        createMetadataDummy(properties, creditData),
        LocalDateTime.now().minusDays(daysSinceCreation).toString(),
        fairy.baseProducer().randomElement(documentOrigins),
        StringUtils.EMPTY);
  }

  private Creditor createDummyCreditor() {
    Fairy fairy = Fairy.create();
    Person person = fairy.person();
    Double income = fairy.baseProducer().randomBetween(INCOME_MIN, INCOME_MAX);
    Double extraIncome = fairy.baseProducer().randomBetween(EXTRA_INCOME_MIN, EXTRA_INCOME_MAX);
    Double totalIncome = income + extraIncome;

    LivingSituation livingSituation = fairy.baseProducer().randomElement(livingSituations);
    Double lease = 0.0;
    if (livingSituation.equals(LivingSituation.LEASE)
        || livingSituation.equals(LivingSituation.SHARED_LEASE)) {
      lease = income / 3.3;
    }
    Double outgoings = fairy.baseProducer().randomBetween(200.0, (totalIncome - lease) / 0.45);
    return new Creditor(
        UUID.randomUUID().toString(),
        person.getLastName(),
        person.getFirstName(),
        person.getSex().toString(),
        LocalDateTime.of(
                person.getDateOfBirth().getYear(),
                person.getDateOfBirth().getMonth(),
                person.getDateOfBirth().getDayOfMonth(),
                0,
                0)
            .toString(),
        fairy.person().getAddress().getCity(),
        person.getAddress().getStreet() + " " + person.getAddress().getStreetNumber(),
        person.getAddress().getPostalCode(),
        person.getAddress().getCity(),
        fairy.baseProducer().randomElement(countries),
        fairy.baseProducer().randomElement(occupations),
        person.getEmail(),
        person.getTelephoneNumber(),
        person.getNationalIdentificationNumber(),
        fairy.baseProducer().randomElement(occupationTypes),
        fairy.baseProducer().randomElement(livingSituations),
        income,
        extraIncome,
        outgoings,
        lease);
  }

  private CreditObject createCreditObject() {
    Fairy fairy = Fairy.create();
    Double totalPrice = fairy.baseProducer().randomBetween(TOTAL_PRICE_MIN, TOTAL_PRICE_MAX);
    Double vat = fairy.baseProducer().randomBetween(VAT_MIN, VAT_MAX);
    LocalDateTime localDateTime = LocalDateTime.now();
    long daysSincePurchase = fairy.baseProducer().randomBetween(0, 10);
    return new CreditObject(
        fairy.baseProducer().randomElement(objects),
        fairy.baseProducer().randomElement(objectTypes),
        fairy.textProducer().randomString(15),
        fairy.textProducer().randomString(30),
        fairy.baseProducer().randomElement(brands),
        totalPrice,
        totalPrice * (1 - vat),
        vat,
        localDateTime.minusDays(daysSincePurchase).toString());
  }

  private Offer createCreditOffer(CreditObject creditObject) {
    Fairy fairy = Fairy.create();
    Double interest = fairy.baseProducer().randomBetween(INTEREST_MIN, INTEREST_MAX);
    return new Offer(
        fairy.baseProducer().randomElement(offerTypes),
        creditObject.getTotalPrice(),
        interest,
        creditObject.getTotalPrice() + creditObject.getTotalPrice() * interest,
        fairy.baseProducer().randomBetween(DURATION_MIN, DURATION_MAX),
        LocalDateTime.now()
            .minusDays(
                fairy
                    .baseProducer()
                    .randomBetween(REQUESTED_PAYOUT_DATE_MIN, REQUESTED_PAYOUT_DATE_MAX))
            .toString(),
        null);
  }

  public String createOfferId() {
    return createRandomUuidString();
  }

  public String createRandomUuidString() {
    return UUID.randomUUID().toString();
  }

  private Map<String, String> createMetadataDummy(List<String> properties, Object creditData) {
    if (creditData == null) {
      return new HashMap<>();
    }
    Map<String, String> metadata = new HashMap<>();
    Map<String, String> creditObj =
        objectMapper.convertValue(creditData, new TypeReference<Map<String, String>>() {});
    for (String property : properties) {
      try {
        metadata.put(property, creditObj.get(property));
      } catch (Exception e) {

        metadata.put(property, "dummy");
      }
    }
    return metadata;
  }
}
