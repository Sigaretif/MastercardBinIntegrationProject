package com.org.wortel.mastercardbin.infrastructure.api.internal.dto.transactionaggregate.valueobject;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionsPerCountryDto {

    private String countryName;
    private Long transactionsAmount;
}
