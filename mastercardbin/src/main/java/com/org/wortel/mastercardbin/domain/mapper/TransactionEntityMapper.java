package com.org.wortel.mastercardbin.domain.mapper;

import com.org.wortel.mastercardbin.domain.model.Transaction;
import com.org.wortel.mastercardbin.infrastructure.persistence.entity.TransactionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "jakarta")
public interface TransactionEntityMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "timestamp", ignore = true)
    TransactionEntity toEntity(Transaction transaction);

    Transaction toModel(TransactionEntity transactionEntity);
}