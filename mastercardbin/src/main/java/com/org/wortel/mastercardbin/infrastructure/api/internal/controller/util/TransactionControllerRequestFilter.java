package com.org.wortel.mastercardbin.infrastructure.api.internal.controller.util;

import com.org.wortel.mastercardbin.infrastructure.api.util.RequestTrackingUtil;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.ext.Provider;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;

// System currently has only one container filter - therefore there is no need to add @Priority
@Provider
@RequiredArgsConstructor
public class TransactionControllerRequestFilter implements ContainerRequestFilter, ContainerResponseFilter {

    private final RequestTrackingUtil requestTrackingUtil;

    @Override
    public void filter(ContainerRequestContext containerRequestContext) {
        var requestHeaderName = requestTrackingUtil.getTrackingRequestHeaderName();
        var requestId = containerRequestContext.getHeaderString(requestHeaderName);
        if (StringUtils.isEmpty(requestId)) {
            requestId = requestTrackingUtil.generateTrackingRequestHeader();
            containerRequestContext.getHeaders().add(requestHeaderName, requestId);
        }

        requestTrackingUtil.putTrackingId(requestId);
    }

    @Override
    public void filter(ContainerRequestContext containerRequestContext, ContainerResponseContext containerResponseContext) {
        var requestId = requestTrackingUtil.getTrackingId();
        containerResponseContext.getHeaders().add(requestTrackingUtil.getTrackingRequestHeaderName(), requestId);

        requestTrackingUtil.removeTrackingId();
    }
}
