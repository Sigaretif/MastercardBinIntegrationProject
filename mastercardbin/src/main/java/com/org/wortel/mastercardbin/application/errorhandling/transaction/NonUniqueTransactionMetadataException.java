package com.org.wortel.mastercardbin.application.errorhandling.transaction;

import com.org.wortel.mastercardbin.application.errorhandling.entity.NonUniqueEntityException;
import com.org.wortel.mastercardbin.domain.transaction.dto.TransactionMetadata;

public class NonUniqueTransactionMetadataException extends NonUniqueEntityException {

    private static final String DEFAULT_MESSAGE = "Provided BIN number is ambiguous.";

    public NonUniqueTransactionMetadataException() {
        super(TransactionMetadata.class.getSimpleName(), DEFAULT_MESSAGE);
    }
}
