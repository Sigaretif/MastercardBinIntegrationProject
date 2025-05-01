package com.org.wortel.mastercardbin.domain.transaction.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class BaseTransaction {
    private Long id;
    private String binNumber;
    private BigDecimal amount;
    private String location;
    private LocalDateTime timestamp;
}
