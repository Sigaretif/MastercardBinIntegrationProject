package com.org.wortel.mastercardbin.infrastructure.api.internal.dto.newtransaction.mapper;

import com.org.wortel.mastercardbin.domain.transaction.model.Transaction;
import com.org.wortel.mastercardbin.domain.transaction.dto.BaseTransaction;
import com.org.wortel.mastercardbin.infrastructure.api.internal.dto.newtransaction.NewTransactionRequestDto;
import com.org.wortel.mastercardbin.infrastructure.api.internal.dto.newtransaction.NewTransactionResponseDto;
import com.org.wortel.mastercardbin.infrastructure.api.internal.dto.newtransaction.valueobject.NewTransactionMetadataDto;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class NewTransactionDtoMapper {

    public BaseTransaction toDomain(NewTransactionRequestDto dto) {
        return BaseTransaction.builder()
                .binNumber(dto.getBinNumber())
                .amount(dto.getAmount())
                .location(dto.getLocation())
                .timestamp(dto.getTimestamp())
                .build();
    }

    public NewTransactionResponseDto toResponse(Transaction transaction) {
        return NewTransactionResponseDto.builder()
                .id(transaction.getTransactionId())
                .binNumber(transaction.getBinNumber())
                .amount(transaction.getAmount())
                .location(transaction.getLocation())
                .timestamp(transaction.getTimestamp())
                .metadata(NewTransactionMetadataDto.builder()
                        .id(transaction.getMetadataId())
                        .customerName(transaction.getCustomerName())
                        .countryName(transaction.getCountryName())
                        .countryCode(transaction.getCountryCode())
                        .build())
                .build();
    }
}