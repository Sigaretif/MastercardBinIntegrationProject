package com.org.wortel.mastercardbin.domain.service.internal;

import com.org.wortel.mastercardbin.domain.mapper.TransactionEntityMapper;
import com.org.wortel.mastercardbin.infrastructure.external.service.MastercardBinDataService;
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
    private final MastercardBinDataService mastercardBinDataService;

    public Transaction saveTransaction(final Transaction transaction) {
        mastercardBinDataService.getLookupBin(transaction.getBinNumber());

        var transactionEntity = transactionEntityMapper.toEntity(transaction);
        transactionRepository.persist(transactionEntity);
        return transactionEntityMapper.toModel(transactionEntity);
    }
}