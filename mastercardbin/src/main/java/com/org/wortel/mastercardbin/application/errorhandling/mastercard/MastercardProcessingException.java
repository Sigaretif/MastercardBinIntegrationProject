package com.org.wortel.mastercardbin.application.errorhandling.mastercard;

import com.org.wortel.mastercardbin.application.errorhandling.general.BusinessException;
import jakarta.ws.rs.core.Response;

public class MastercardProcessingException extends BusinessException {

    private static final String DEFAULT_MESSAGE = "Unexpected error occurred during mastercard API processing.";

    public MastercardProcessingException(String genericMessage) {
        super(DEFAULT_MESSAGE, genericMessage, Response.Status.BAD_REQUEST);
    }
}
