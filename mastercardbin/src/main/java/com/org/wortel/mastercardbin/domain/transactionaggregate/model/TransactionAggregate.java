package com.org.wortel.mastercardbin.domain.transactionaggregate.model;

import com.org.wortel.mastercardbin.domain.transactionaggregate.model.valueobject.TransactionsPerCountry;
import com.org.wortel.mastercardbin.domain.transactionaggregate.model.valueobject.TransactionsPerCustomer;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class TransactionAggregate {

    private List<TransactionsPerCountry> countriesTransactionsSummary;
    private TransactionsPerCustomer mostFrequentCustomer;
    private BigDecimal averageTransactionsAmount;
}
