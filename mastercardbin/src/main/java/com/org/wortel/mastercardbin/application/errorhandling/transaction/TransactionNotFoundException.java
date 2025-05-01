package com.org.wortel.mastercardbin.application.errorhandling.transaction;

import com.org.wortel.mastercardbin.application.errorhandling.entity.EntityNotFoundException;
import com.org.wortel.mastercardbin.domain.transaction.dto.TransactionMetadata;
import com.org.wortel.mastercardbin.domain.transaction.model.Transaction;

public class TransactionNotFoundException extends EntityNotFoundException {

    public TransactionNotFoundException() {
        super(Transaction.class.getSimpleName());
    }
}
