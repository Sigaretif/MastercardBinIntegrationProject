package com.org.wortel.mastercardbin.infrastructure.api.internal.controller;

import com.org.wortel.mastercardbin.infrastructure.api.internal.dto.newtransaction.NewTransactionRequestDto;
import com.org.wortel.mastercardbin.infrastructure.api.internal.dto.transactionaggregate.TransactionAggregateFilterRequestDto;
import com.org.wortel.mastercardbin.infrastructure.api.internal.helper.TransactionHelper;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import jakarta.ws.rs.BeanParam;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;

@Path("/transactions")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionHelper transactionHelper;

    @POST
    @RolesAllowed("USER")
    public Response createTransaction(@Valid NewTransactionRequestDto request) {
        var response = transactionHelper.createTransaction(request);
        return Response.status(Response.Status.CREATED)
                .entity(response)
                .build();
    }

    @GET
    @Path("/aggregate")
    @RolesAllowed("USER")
    public Response getTransactionAggregate(@BeanParam @Valid TransactionAggregateFilterRequestDto request) {
        transactionHelper.getAggregatedTransactionsData(request);
        return Response.status(Response.Status.OK)
                .entity(transactionHelper.getAggregatedTransactionsData(request))
                .build();
    }
}