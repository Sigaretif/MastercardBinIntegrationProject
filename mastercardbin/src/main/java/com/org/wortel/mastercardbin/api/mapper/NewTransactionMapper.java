package com.org.wortel.mastercardbin.api.mapper;

import com.org.wortel.mastercardbin.api.model.NewTransactionRequest;
import com.org.wortel.mastercardbin.api.model.NewTransactionResponse;
import com.org.wortel.mastercardbin.domain.model.Transaction;
import org.mapstruct.Mapper;

@Mapper(componentModel = "jakarta")
public interface NewTransactionMapper {

    Transaction toDomain(NewTransactionRequest transactionRequest);

    NewTransactionResponse toResponse(Transaction transaction);
}