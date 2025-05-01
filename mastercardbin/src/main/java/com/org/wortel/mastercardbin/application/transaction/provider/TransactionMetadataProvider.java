package com.org.wortel.mastercardbin.application.transaction.provider;

import com.org.wortel.mastercardbin.application.bindata.service.MastercardBinDataService;
import com.org.wortel.mastercardbin.application.errorhandling.transaction.NonUniqueTransactionMetadataException;
import com.org.wortel.mastercardbin.application.errorhandling.transaction.TransactionMetadataNotFoundException;
import com.org.wortel.mastercardbin.domain.transaction.dto.TransactionMetadata;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;

@ApplicationScoped
@RequiredArgsConstructor
public class TransactionMetadataProvider {

    private final MastercardBinDataService mastercardBinDataService;

    public TransactionMetadata getTransactionMetadata(String binNumber) {
        var metadataList = mastercardBinDataService.getBinData(binNumber);
        return metadataList.stream()
                .limit(2)
                .reduce((a, b) -> {
                    throw new NonUniqueTransactionMetadataException();
                })
                .orElseThrow(() -> new TransactionMetadataNotFoundException());
    }
}
