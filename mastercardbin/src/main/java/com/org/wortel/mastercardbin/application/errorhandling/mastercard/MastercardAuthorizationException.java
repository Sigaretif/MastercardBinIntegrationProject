package com.org.wortel.mastercardbin.application.errorhandling.mastercard;

import com.org.wortel.mastercardbin.application.errorhandling.general.BusinessException;
import jakarta.ws.rs.core.Response;

public class MastercardAuthorizationException extends BusinessException {

    private static final String DEFAULT_MESSAGE = "Mastercard authorization failed. %s.";

    public MastercardAuthorizationException(String message, String genericMessage) {
        super(String.format(DEFAULT_MESSAGE, message), genericMessage, Response.Status.FORBIDDEN);
    }
}
