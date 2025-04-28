package com.org.wortel.mastercardbin.api.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class NewTransactionRequest {
    private String binNumber;
    private BigDecimal amount;
    private String location;
}