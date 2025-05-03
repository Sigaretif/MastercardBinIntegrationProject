package com.org.wortel.mastercardbin.infrastructure.api.internal.dto.transactionaggregate;

import com.org.wortel.mastercardbin.infrastructure.api.internal.dto.transactionaggregate.valueobject.TransactionsPerCountryDto;
import com.org.wortel.mastercardbin.infrastructure.api.internal.dto.transactionaggregate.valueobject.TransactionsPerCustomerDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionAggregateResponseDto {

    private List<TransactionsPerCountryDto> countriesTransactionsSummary;
    private List<TransactionsPerCustomerDto> mostFrequentCustomers;
    private BigDecimal averageTransactionsAmount;
}
