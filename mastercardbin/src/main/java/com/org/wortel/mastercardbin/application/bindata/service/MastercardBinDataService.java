package com.org.wortel.mastercardbin.application.bindata.service;

import com.org.wortel.mastercardbin.application.errorhandling.mastercard.MastercardAuthorizationException;
import com.org.wortel.mastercardbin.application.errorhandling.mastercard.MastercardBadRequestException;
import com.org.wortel.mastercardbin.application.errorhandling.mastercard.MastercardProcessingException;
import com.org.wortel.mastercardbin.application.errorhandling.transaction.TransactionMetadataNotFoundException;
import com.org.wortel.mastercardbin.domain.transaction.dto.TransactionMetadata;
import com.org.wortel.mastercardbin.infrastructure.api.external.client.MastercardClient;
import com.org.wortel.mastercardbin.infrastructure.api.external.client.util.MastercardClientFactory;
import com.org.wortel.mastercardbin.infrastructure.api.external.dto.MastercardBinDataRequestDto;
import com.org.wortel.mastercardbin.infrastructure.api.external.dto.mapper.MastercardBinDataDtoMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.jboss.resteasy.reactive.ClientWebApplicationException;

import java.util.List;

@ApplicationScoped
public class MastercardBinDataService {

    private final MastercardClient mastercardClient;

    @Inject
    public MastercardBinDataService(MastercardClientFactory mastercardClientFactory) {
        this.mastercardClient = mastercardClientFactory.createClient();
    }

    public List<TransactionMetadata> getBinData(String binNumber) {
        try {
            var metadataList = mastercardClient.getBinData(MastercardBinDataRequestDto.builder()
                    .accountRange(binNumber)
                    .build());
            return metadataList.stream()
                    .map(MastercardBinDataDtoMapper::toDomain)
                    .toList();
        } catch (Exception e) {
            if (e instanceof ClientWebApplicationException clientException) {
                switch (clientException.getResponse().getStatus()) {
                    case 401, 403 -> throw new MastercardAuthorizationException(
                            "Unauthorized access to Mastercard API", clientException.getMessage());
                    case 400 -> throw new MastercardBadRequestException(clientException.getMessage());
                    case 404 -> throw new TransactionMetadataNotFoundException();
                    default -> {}
                }
            }
            throw new MastercardProcessingException(e.getMessage());
        }
    }
}
