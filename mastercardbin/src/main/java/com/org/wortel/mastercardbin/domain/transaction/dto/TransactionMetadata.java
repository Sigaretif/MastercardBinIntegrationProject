package com.org.wortel.mastercardbin.domain.transaction.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TransactionMetadata {
    private Long id;
    private String binNumber;
    private String customerName;
    private String countryName;
    private Integer countryCode;
}
