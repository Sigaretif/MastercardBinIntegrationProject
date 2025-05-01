package com.org.wortel.mastercardbin.infrastructure.api.internal.dto.transactionaggregate.mapper;

import com.org.wortel.mastercardbin.domain.transactionaggregate.dto.TransactionAggregateFilter;
import com.org.wortel.mastercardbin.domain.transactionaggregate.model.TransactionAggregate;
import com.org.wortel.mastercardbin.domain.transactionaggregate.model.valueobject.TransactionsPerCountry;
import com.org.wortel.mastercardbin.domain.transactionaggregate.model.valueobject.TransactionsPerCustomer;
import com.org.wortel.mastercardbin.infrastructure.api.internal.dto.transactionaggregate.TransactionAggregateFilterRequestDto;
import com.org.wortel.mastercardbin.infrastructure.api.internal.dto.transactionaggregate.TransactionAggregateResponseDto;
import com.org.wortel.mastercardbin.infrastructure.api.internal.dto.transactionaggregate.valueobject.TransactionsPerCountryDto;
import com.org.wortel.mastercardbin.infrastructure.api.internal.dto.transactionaggregate.valueobject.TransactionsPerCustomerDto;
import lombok.experimental.UtilityClass;

@UtilityClass
public class TransactionAggregateDtoMapper {

    public TransactionAggregateFilter toDomain(TransactionAggregateFilterRequestDto dto) {
        return TransactionAggregateFilter.builder()
                .fromDate(dto.getFrom())
                .toDate(dto.getTo())
                .binPrefix(dto.getBinPrefix())
                .location(dto.getLocation())
                .build();
    }

    public TransactionAggregateResponseDto toResponse(TransactionAggregate transactionAggregate) {
        return TransactionAggregateResponseDto.builder()
                .averageTransactionsAmount(transactionAggregate.getAverageTransactionsAmount())
                .mostFrequentCustomer(convertTransactionsPerCustomer(transactionAggregate.getMostFrequentCustomer()))
                .countriesTransactionsSummary(transactionAggregate.getCountriesTransactionsSummary()
                        .stream()
                        .map(TransactionAggregateDtoMapper::convertTransactionsPerCustomer)
                        .toList())
                .build();
    }

    private TransactionsPerCustomerDto convertTransactionsPerCustomer(TransactionsPerCustomer transactionsPerCustomer) {
        return TransactionsPerCustomerDto.builder()
                .customerName(transactionsPerCustomer.getCustomerName())
                .transactionsAmount(transactionsPerCustomer.getTransactionsAmount())
                .build();
    }

    private TransactionsPerCountryDto convertTransactionsPerCustomer(TransactionsPerCountry transactionsPerCountry) {
        return TransactionsPerCountryDto.builder()
                .countryName(transactionsPerCountry.getCountryName())
                .transactionsAmount(transactionsPerCountry.getTransactionsAmount())
                .build();
    }
}
