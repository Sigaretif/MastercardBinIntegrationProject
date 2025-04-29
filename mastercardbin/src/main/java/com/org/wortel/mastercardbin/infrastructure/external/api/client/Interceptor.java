package com.org.wortel.mastercardbin.infrastructure.external.api.client;

import com.org.wortel.mastercardbin.infrastructure.external.api.model.MastercardBinDataRequest;
import com.org.wortel.mastercardbin.infrastructure.external.service.MastercardAuthorizationHeaderGenerator;
import jakarta.inject.Inject;
import jakarta.ws.rs.client.ClientRequestContext;
import jakarta.ws.rs.client.ClientRequestFilter;
import jakarta.ws.rs.ext.Provider;

import java.io.IOException;

@Provider
public class Interceptor implements ClientRequestFilter {

    @Inject
    MastercardAuthorizationHeaderGenerator mastercardAuthorizationHeaderGenerator;

    @Override
    public void filter(ClientRequestContext clientRequestContext) throws IOException {
//        // Add your custom logic here
//        // For example, you can modify the request headers or log the request details
//        String authorizationHeader = clientRequestContext.getHeaderString("Authorization");
//
//        // You can also log the request details if needed
//        System.out.println("Request URI: " + clientRequestContext.getUri());
//        System.out.println("Authorization Header: " + authorizationHeader);

        var requestBody = (MastercardBinDataRequest) clientRequestContext.getEntity();
        var authorizationHeader = mastercardAuthorizationHeaderGenerator.getAuthorizationHeader(requestBody);

        clientRequestContext.getHeaders().add("Authorization", authorizationHeader);
    }
}
