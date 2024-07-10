package com.mag.bpm.dto;

import com.mag.bpm.models.enums.DocumentCode;

public record CreditRuleDto(String ruleCode,String description, DocumentCode documentCode){}
