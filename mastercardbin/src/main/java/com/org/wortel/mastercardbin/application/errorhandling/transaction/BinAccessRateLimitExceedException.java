package com.org.wortel.mastercardbin.application.errorhandling.transaction;

import com.org.wortel.mastercardbin.application.errorhandling.general.BusinessException;
import jakarta.ws.rs.core.Response;

public class BinAccessRateLimitExceedException extends BusinessException {

    private static final String DEFAULT_MESSAGE = "Too may requests with BIN: %s. Please try again later.";
    private static final String GENERIC_MESSAGE = "Bin data access rate limit exceeded.";

    public BinAccessRateLimitExceedException(String binNumber) {
        super(String.format(DEFAULT_MESSAGE, binNumber), GENERIC_MESSAGE, Response.Status.TOO_MANY_REQUESTS);
    }
}