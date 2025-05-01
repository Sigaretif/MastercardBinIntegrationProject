package com.org.wortel.mastercardbin.infrastructure.api.internal.dto.transactionaggregate.valueobject;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TransactionsPerCustomerDto {

    private String customerName;
    private Long transactionsAmount;
}
