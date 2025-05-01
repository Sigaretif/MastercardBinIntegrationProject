package com.org.wortel.mastercardbin.infrastructure.api.util;

import jakarta.enterprise.context.ApplicationScoped;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.MDC;

import java.util.UUID;

@ApplicationScoped
public class RequestTrackingUtil {

    @ConfigProperty(name = "request-tracking.mdc-request-id")
    String mdcRequestId;
    @ConfigProperty(name = "request-tracking.header-name")
    String headerName;

    public void putTrackingId(String requestId) {
        if (StringUtils.isNotEmpty(requestId)) {
            MDC.put(mdcRequestId, requestId);
        }
    }

    public String getTrackingId() {
        return MDC.get(mdcRequestId);
    }

    public void removeTrackingId() {
        MDC.remove(mdcRequestId);
    }

    public String getTrackingRequestHeaderName() {
        return headerName;
    }

    public String generateTrackingRequestHeader() {
        return UUID.randomUUID().toString();
    }
}
