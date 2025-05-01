package com.org.wortel.mastercardbin.domain.transactionaggregate.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class TransactionAggregateFilter {

    private LocalDateTime fromDate;
    private LocalDateTime toDate;
    private String location;
    private String binPrefix;
}
