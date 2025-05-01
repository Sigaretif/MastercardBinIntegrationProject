package com.org.wortel.mastercardbin.domain.transactionaggregate.model.valueobject;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TransactionsPerCountry {

    private String countryName;
    private Long transactionsAmount;
}
