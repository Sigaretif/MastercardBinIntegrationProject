package com.org.wortel.mastercardbin.infrastructure.persistence.entity.mapper;

import com.org.wortel.mastercardbin.domain.transaction.model.Transaction;
import com.org.wortel.mastercardbin.infrastructure.persistence.entity.TransactionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "jakarta")
public interface TransactionEntityMapper {

    @Mapping(target = "transactionId", source = "id")
    @Mapping(target = "metadataId", source = "transactionMetadata.id")
    @Mapping(target = "customerName", source = "transactionMetadata.customerName")
    @Mapping(target = "countryName", source = "transactionMetadata.countryName")
    @Mapping(target = "countryCode", source = "transactionMetadata.countryCode")
    Transaction toDomain(TransactionEntity transactionEntity);
}