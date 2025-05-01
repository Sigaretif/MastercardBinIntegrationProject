package com.org.wortel.mastercardbin.application.transaction.service;

import com.org.wortel.mastercardbin.application.errorhandling.transaction.TransactionNotFoundException;
import com.org.wortel.mastercardbin.application.transaction.provider.TransactionMetadataProvider;
import com.org.wortel.mastercardbin.domain.transaction.dto.BaseTransaction;
import com.org.wortel.mastercardbin.domain.transaction.model.Transaction;
import com.org.wortel.mastercardbin.domain.transactionaggregate.dto.TransactionAggregateFilter;
import com.org.wortel.mastercardbin.domain.transactionaggregate.model.TransactionAggregate;
import com.org.wortel.mastercardbin.domain.transactionaggregate.model.valueobject.TransactionsPerCountry;
import com.org.wortel.mastercardbin.domain.transactionaggregate.model.valueobject.TransactionsPerCustomer;
import com.org.wortel.mastercardbin.infrastructure.persistence.entity.TransactionEntity;
import com.org.wortel.mastercardbin.infrastructure.persistence.entity.TransactionMetadataEntity;
import com.org.wortel.mastercardbin.infrastructure.persistence.entity.mapper.TransactionEntityMapper;
import com.org.wortel.mastercardbin.infrastructure.persistence.repository.TransactionMetadataRepository;
import com.org.wortel.mastercardbin.infrastructure.persistence.repository.TransactionRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@ApplicationScoped
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final TransactionMetadataRepository transactionMetadataRepository;
    private final TransactionMetadataProvider transactionMetadataProvider;
    private final TransactionEntityMapper transactionEntityMapper;

    @Transactional
    public Transaction createTransaction(final BaseTransaction baseTransaction) {
        var transactionMetadata = transactionMetadataProvider.getTransactionMetadata(baseTransaction.getBinNumber());

        var transactionMetadataEntity = transactionMetadataRepository
                .findByAllCredentials(transactionMetadata.getCustomerName(), transactionMetadata.getCountryName(),
                        transactionMetadata.getCountryCode())
                .orElseGet(() -> {
                    var newMetadataEntity = TransactionMetadataEntity.builder()
                            .customerName(transactionMetadata.getCustomerName())
                            .countryName(transactionMetadata.getCountryName())
                            .countryCode(transactionMetadata.getCountryCode())
                            .build();
                    transactionMetadataRepository.persist(newMetadataEntity);
                    return newMetadataEntity;
                });

        var transactionEntity = TransactionEntity.builder()
                .binNumber(transactionMetadata.getBinNumber())
                .amount(baseTransaction.getAmount())
                .location(baseTransaction.getLocation())
                .timestamp(baseTransaction.getTimestamp())
                .transactionMetadata(transactionMetadataEntity)
                .build();
        transactionRepository.persist(transactionEntity);

        return transactionEntityMapper.toDomain(transactionEntity);
    }

    public TransactionAggregate getAggregatedTransactionsData(final TransactionAggregateFilter filter) {
        var transactions = transactionRepository.findByDatesAndCountryAndBinPrefix(
                        filter.getFromDate(), filter.getToDate(), filter.getLocation(), filter.getBinPrefix())
                .stream()
                .map(transactionEntityMapper::toDomain)
                .toList();

        if (transactions.isEmpty()) {
            throw new TransactionNotFoundException();
        }

        return TransactionAggregate.builder()
                .averageTransactionsAmount(getTransactionsAverageAmount(transactions))
                .mostFrequentCustomer(getMostFrequentCustomer(transactions))
                .countriesTransactionsSummary(getCountriesTransactionsSummary(transactions))
                .build();
    }

    private BigDecimal getTransactionsAverageAmount(List<Transaction> transactions) {
        return transactions.stream()
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .divide(BigDecimal.valueOf(transactions.size()), 2, RoundingMode.HALF_UP);
    }

    private TransactionsPerCustomer getMostFrequentCustomer(List<Transaction> transactions) {
        Map<String, Long> transactionsPerCustomer = transactions.stream()
                .map(Transaction::getCustomerName)
                .filter(Objects::nonNull)
                .collect(Collectors.groupingBy(customer -> customer, Collectors.counting()));

        return transactionsPerCustomer.entrySet().stream()
                // TODO Currently it returns first occurence of max, but there can be "multiple maxes"
                .max(Map.Entry.comparingByValue())
                .map(entry -> TransactionsPerCustomer.builder()
                        .customerName(entry.getKey())
                        .transactionsAmount(entry.getValue())
                        .build())
                .orElse(null);
    }

    private List<TransactionsPerCountry> getCountriesTransactionsSummary(List<Transaction> transactions) {
        Map<String, Long> transactionsPerCountry = transactions.stream()
                .map(Transaction::getCountryName)
                .filter(Objects::nonNull)
                .collect(Collectors.groupingBy(country -> country, Collectors.counting()));

        return transactionsPerCountry.entrySet().stream()
                .map(entry -> TransactionsPerCountry.builder()
                        .countryName(entry.getKey())
                        .transactionsAmount(entry.getValue())
                        .build())
                .collect(Collectors.toList());
    }
}