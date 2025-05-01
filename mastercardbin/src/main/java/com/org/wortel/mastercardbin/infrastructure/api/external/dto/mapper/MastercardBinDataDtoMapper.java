package com.org.wortel.mastercardbin.infrastructure.api.external.dto.mapper;

import com.org.wortel.mastercardbin.domain.transaction.dto.TransactionMetadata;
import com.org.wortel.mastercardbin.infrastructure.api.external.dto.MastercardBinDataResponseDto;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class MastercardBinDataDtoMapper {

    public TransactionMetadata toDomain(MastercardBinDataResponseDto response) {
        return TransactionMetadata.builder()
                .customerName(response.getCustomerName())
                .countryName(response.getCountry().getName())
                .countryCode(response.getCountry().getCode())
                .binNumber(response.getBinNum())
                .build();
    }
}
