package com.org.wortel.mastercardbin.infrastructure.api.external.client.util;

import com.org.wortel.mastercardbin.application.bindata.auhorization.MastercardAuthorizationHeaderGenerator;
import com.org.wortel.mastercardbin.infrastructure.api.util.RequestTrackingUtil;
import jakarta.ws.rs.client.ClientRequestContext;
import jakarta.ws.rs.client.ClientRequestFilter;
import jakarta.ws.rs.client.ClientResponseContext;
import jakarta.ws.rs.client.ClientResponseFilter;
import jakarta.ws.rs.ext.Provider;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@Provider
@RequiredArgsConstructor
public class MastercardClientRequestFilter implements ClientRequestFilter, ClientResponseFilter {

    private final MastercardAuthorizationHeaderGenerator mastercardAuthorizationHeaderGenerator;
    private final RequestTrackingUtil requestTrackingUtil;

    @Override
    public void filter(ClientRequestContext clientRequestContext) {
        var payload = clientRequestContext.getEntity();
        var uri = clientRequestContext.getUri();
        var httpMethod = clientRequestContext.getMethod();

        var authorizationHeader = mastercardAuthorizationHeaderGenerator.getAuthorizationHeader2(uri, httpMethod, payload);

        clientRequestContext.getHeaders().add("Authorization", authorizationHeader);

        var requestId = requestTrackingUtil.getTrackingId();
        if (StringUtils.isNotEmpty(requestId)) {
            clientRequestContext.getHeaders().add(requestTrackingUtil.getTrackingRequestHeaderName(), requestId);
        }
    }

    @Override
    public void filter(ClientRequestContext clientRequestContext, ClientResponseContext clientResponseContext) {
        var requestId = requestTrackingUtil.getTrackingId();
        if (StringUtils.isNotEmpty(requestId)) {
            clientResponseContext.getHeaders().add(requestTrackingUtil.getTrackingRequestHeaderName(), requestId);
        }
    }
}
