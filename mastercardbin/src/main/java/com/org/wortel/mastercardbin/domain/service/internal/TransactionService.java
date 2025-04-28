package com.org.wortel.mastercardbin.domain.service.internal;

import com.org.wortel.mastercardbin.domain.mapper.TransactionEntityMapper;
import com.org.wortel.mastercardbin.infrastructure.persistence.repository.TransactionRepository;
import com.org.wortel.mastercardbin.domain.model.Transaction;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@ApplicationScoped
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final TransactionEntityMapper transactionEntityMapper;

    @Transactional
    public Transaction saveTransaction(final Transaction transaction) {
        var transactionEntity = transactionEntityMapper.toEntity(transaction);
        transactionRepository.persist(transactionEntity);
        return transactionEntityMapper.toModel(transactionEntity);
    }
}