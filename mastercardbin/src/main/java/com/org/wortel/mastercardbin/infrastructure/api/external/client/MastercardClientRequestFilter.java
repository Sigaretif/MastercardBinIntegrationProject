package com.org.wortel.mastercardbin.infrastructure.api.external.client;

import com.org.wortel.mastercardbin.application.bindata.auhorization.MastercardAuthorizationHeaderGenerator;
import jakarta.ws.rs.client.ClientRequestContext;
import jakarta.ws.rs.client.ClientRequestFilter;
import jakarta.ws.rs.ext.Provider;
import lombok.RequiredArgsConstructor;

@Provider
@RequiredArgsConstructor
public class MastercardClientRequestFilter implements ClientRequestFilter {

    private final MastercardAuthorizationHeaderGenerator mastercardAuthorizationHeaderGenerator;

    @Override
    public void filter(ClientRequestContext clientRequestContext) {
        var payload = clientRequestContext.getEntity();
        var uri = clientRequestContext.getUri();
        var httpMethod = clientRequestContext.getMethod();

        var authorizationHeader = mastercardAuthorizationHeaderGenerator.getAuthorizationHeader2(uri, httpMethod, payload);

        clientRequestContext.getHeaders().add("Authorization", authorizationHeader);
    }
}
