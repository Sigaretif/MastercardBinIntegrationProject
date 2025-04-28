package com.org.wortel.mastercardbin.domain.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Transaction {
    private Long id;
    private String binNumber;
    private BigDecimal amount;
    private String location;
    private Long timestamp;
}
