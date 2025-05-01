package com.org.wortel.mastercardbin.infrastructure.api.internal.dto.transactionaggregate;

import com.org.wortel.mastercardbin.infrastructure.api.internal.dto.transactionaggregate.valueobject.TransactionsPerCountryDto;
import com.org.wortel.mastercardbin.infrastructure.api.internal.dto.transactionaggregate.valueobject.TransactionsPerCustomerDto;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class TransactionAggregateResponseDto {

    private List<TransactionsPerCountryDto> countriesTransactionsSummary;
    private TransactionsPerCustomerDto mostFrequentCustomer;
    private BigDecimal averageTransactionsAmount;
}
