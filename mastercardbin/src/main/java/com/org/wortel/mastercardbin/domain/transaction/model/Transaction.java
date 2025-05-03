package com.org.wortel.mastercardbin.domain.transaction.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
public class Transaction {

    private Long transactionId;
    private String binNumber;
    private BigDecimal amount;
    private String location;
    private LocalDateTime timestamp;
    private Long metadataId;
    private String customerName;
    private String countryName;
    private Integer countryCode;
}
