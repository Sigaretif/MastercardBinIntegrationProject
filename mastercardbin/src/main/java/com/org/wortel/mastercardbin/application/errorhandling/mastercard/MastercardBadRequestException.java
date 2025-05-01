package com.org.wortel.mastercardbin.application.errorhandling.mastercard;

import com.org.wortel.mastercardbin.application.errorhandling.general.BusinessException;
import jakarta.ws.rs.core.Response;

public class MastercardBadRequestException extends BusinessException {

    private static final String DEFAULT_MESSAGE = "Bad request for mastercard API.";

    public MastercardBadRequestException(String genericMessage) {
        super(DEFAULT_MESSAGE, genericMessage, Response.Status.BAD_REQUEST);
    }
}
