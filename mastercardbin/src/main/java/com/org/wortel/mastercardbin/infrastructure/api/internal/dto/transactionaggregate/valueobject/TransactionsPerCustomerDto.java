package com.org.wortel.mastercardbin.infrastructure.api.internal.dto.transactionaggregate.valueobject;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionsPerCustomerDto {

    private String customerName;
    private Long transactionsAmount;
}
