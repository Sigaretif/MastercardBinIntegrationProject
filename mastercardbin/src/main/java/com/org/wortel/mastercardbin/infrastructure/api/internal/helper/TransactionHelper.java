package com.org.wortel.mastercardbin.infrastructure.api.internal.helper;

import com.org.wortel.mastercardbin.application.transaction.service.TransactionService;
import com.org.wortel.mastercardbin.infrastructure.api.internal.dto.newtransaction.NewTransactionRequestDto;
import com.org.wortel.mastercardbin.infrastructure.api.internal.dto.newtransaction.NewTransactionResponseDto;
import com.org.wortel.mastercardbin.infrastructure.api.internal.dto.newtransaction.mapper.NewTransactionDtoMapper;
import com.org.wortel.mastercardbin.infrastructure.api.internal.dto.transactionaggregate.TransactionAggregateFilterRequestDto;
import com.org.wortel.mastercardbin.infrastructure.api.internal.dto.transactionaggregate.TransactionAggregateResponseDto;
import com.org.wortel.mastercardbin.infrastructure.api.internal.dto.transactionaggregate.mapper.TransactionAggregateDtoMapper;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;

@ApplicationScoped
@RequiredArgsConstructor
public class TransactionHelper {

    private final TransactionService transactionService;

    public NewTransactionResponseDto createTransaction(final NewTransactionRequestDto request) {
        var transaction = transactionService.createTransaction(NewTransactionDtoMapper.toDomain(request));
        return NewTransactionDtoMapper.toResponse(transaction);
    }

    public TransactionAggregateResponseDto getAggregatedTransactionsData(final TransactionAggregateFilterRequestDto request) {
        var aggregatedTransactions = transactionService
                .getAggregatedTransactionsData(TransactionAggregateDtoMapper.toDomain(request));
        return TransactionAggregateDtoMapper.toResponse(aggregatedTransactions);
    }
}
