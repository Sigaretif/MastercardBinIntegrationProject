package com.org.wortel.mastercardbin.util;

import com.org.wortel.mastercardbin.infrastructure.persistence.entity.TransactionEntity;
import com.org.wortel.mastercardbin.infrastructure.persistence.entity.TransactionMetadataEntity;
import com.org.wortel.mastercardbin.infrastructure.persistence.repository.TransactionMetadataRepository;
import com.org.wortel.mastercardbin.infrastructure.persistence.repository.TransactionRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@ApplicationScoped
public class TransactionDatabaseHelper {

    @Inject
    TransactionRepository transactionRepository;
    @Inject
    TransactionMetadataRepository transactionMetadataRepository;

    @Transactional
    public void cleanDatabase() {
        transactionRepository.deleteAll();
        transactionMetadataRepository.deleteAll();
    }

    @Transactional
    public void initData(String customerName, String countryName, Integer countryCode, String binNumber,
                         BigDecimal amount, String location, LocalDateTime timestamp) {
        var transactionMetadataEntity = transactionMetadataRepository
                .findByAllCredentials(customerName, countryName, countryCode)
                .orElseGet(() -> {
                    var newMetadataEntity = TransactionMetadataEntity.builder()
                            .customerName(customerName)
                            .countryName(countryName)
                            .countryCode(countryCode)
                            .build();
                    transactionMetadataRepository.persist(newMetadataEntity);
                    return newMetadataEntity;
                });
        var transactionEntity = TransactionEntity.builder()
                .binNumber(binNumber)
                .amount(amount)
                .location(location)
                .timestamp(timestamp)
                .transactionMetadata(transactionMetadataEntity)
                .build();
        transactionRepository.persist(transactionEntity);
    }
}
