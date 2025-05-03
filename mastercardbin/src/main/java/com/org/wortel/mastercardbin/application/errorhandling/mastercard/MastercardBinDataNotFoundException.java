package com.org.wortel.mastercardbin.application.errorhandling.mastercard;

import com.org.wortel.mastercardbin.application.errorhandling.entity.EntityNotFoundException;
import com.org.wortel.mastercardbin.domain.transaction.dto.TransactionMetadata;

public class MastercardBinDataNotFoundException extends EntityNotFoundException {

    public MastercardBinDataNotFoundException() {
        super(TransactionMetadata.class.getSimpleName());
    }
}
