package com.org.wortel.mastercardbin.application.errorhandling.transaction;

import com.org.wortel.mastercardbin.application.errorhandling.entity.EntityNotFoundException;
import com.org.wortel.mastercardbin.domain.transaction.dto.TransactionMetadata;

public class TransactionMetadataNotFoundException extends EntityNotFoundException {

    public TransactionMetadataNotFoundException() {
        super(TransactionMetadata.class.getSimpleName());
    }
}
