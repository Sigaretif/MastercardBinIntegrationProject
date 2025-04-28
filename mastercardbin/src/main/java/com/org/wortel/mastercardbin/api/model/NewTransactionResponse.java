package com.org.wortel.mastercardbin.api.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class NewTransactionResponse {
    private Long id;
    private String binNumber;
    private BigDecimal amount;
    private String location;
    private Long timestamp;
}
