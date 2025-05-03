package com.org.wortel.mastercardbin.application.transaction.provider;

import com.org.wortel.mastercardbin.application.bindata.service.MastercardBinDataService;
import com.org.wortel.mastercardbin.application.errorhandling.transaction.NonUniqueTransactionMetadataException;
import com.org.wortel.mastercardbin.application.errorhandling.mastercard.MastercardBinDataNotFoundException;
import com.org.wortel.mastercardbin.domain.transaction.dto.TransactionMetadata;
import io.quarkus.cache.CacheResult;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;

@ApplicationScoped
@RequiredArgsConstructor
public class TransactionMetadataProvider {

    private final MastercardBinDataService mastercardBinDataService;

    @CacheResult(cacheName = "transaction-metadata-cache")
    public TransactionMetadata getTransactionMetadata(String binNumber) {
        var metadataList = mastercardBinDataService.getBinData(binNumber);
        return metadataList.stream()
                .limit(2)
                .reduce((a, b) -> {
                    throw new NonUniqueTransactionMetadataException();
                })
                .orElseThrow(MastercardBinDataNotFoundException::new);
    }
}
