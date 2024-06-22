package com.mag.bpm;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.mag.bpm.models.CreditObject;
import com.mag.bpm.models.documents.DocumentMetadata;
import com.mag.bpm.models.enums.ObjectType;
import com.mag.bpm.services.AutoDocumentCheckService;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AutoDocumentCheckServiceTest {
  @Autowired private AutoDocumentCheckService autoDocumentCheckService;

  @Test
  void autoCheckHappyPath() {
    List<String> checkProperties =
        List.of("object", "objectType", "model", "serial", "brand", "totalPrice");

    DocumentMetadata documentMetadata = new DocumentMetadata();

    Map<String, String> map = new HashMap<>();
    map.put("object", "Car");
    map.put("objectType", "CAR");
    map.put("model", "test");
    map.put("serial", "12345");
    map.put("brand", "Tesla");
    map.put("totalPrice", "43600");
    documentMetadata.setMetadata(map);

    CreditObject creditObject =
        new CreditObject(
            "Car",
            ObjectType.CAR,
            "test",
            "12345",
            "Tesla",
            43600.0,
            35.760,
            0.22,
            LocalDateTime.now());
    assertTrue(
        autoDocumentCheckService.autoCheckDocument(
            checkProperties, creditObject, documentMetadata));
  }

  @Test
  void autoCheckNonHappyPath() {
    List<String> checkProperties =
        List.of("object", "objectType", "model", "serial", "brand", "totalPrice");

    DocumentMetadata documentMetadata = new DocumentMetadata();

    Map<String, String> map = new HashMap<>();
    map.put("object", "Car");
    map.put("objectType", "CAR");
    map.put("model", "test1");
    map.put("serial", "987654321");
    map.put("brand", "BMW");
    map.put("totalPrice", "43600");
    documentMetadata.setMetadata(map);

    CreditObject creditObject =
        new CreditObject(
            "Car",
            ObjectType.CAR,
            "test",
            "12345",
            "Tesla",
            43600.0,
            35.760,
            0.22,
            LocalDateTime.now());
    assertFalse(
        autoDocumentCheckService.autoCheckDocument(
            checkProperties, creditObject, documentMetadata));
  }
}
