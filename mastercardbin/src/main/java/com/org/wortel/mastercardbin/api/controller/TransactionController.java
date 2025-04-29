package com.org.wortel.mastercardbin.api.controller;

import com.org.wortel.mastercardbin.api.mapper.NewTransactionMapper;
import com.org.wortel.mastercardbin.api.model.NewTransactionRequest;
import com.org.wortel.mastercardbin.domain.service.internal.TransactionService;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/transactions")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TransactionController {

    @Inject
    TransactionService transactionService;

    @Inject
    NewTransactionMapper newTransactionMapper;

    @POST
    public Response createTransaction(final NewTransactionRequest request) {
        var transaction = transactionService.saveTransaction(newTransactionMapper.toDomain(request));
        return Response.status(Response.Status.CREATED)
                .entity(newTransactionMapper.toResponse(transaction))
                .build();
//        var response = mastercardBinDataService.getLookupBin();
    }
}