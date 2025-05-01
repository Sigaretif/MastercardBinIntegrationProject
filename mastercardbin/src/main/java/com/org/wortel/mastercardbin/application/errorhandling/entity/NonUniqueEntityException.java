package com.org.wortel.mastercardbin.application.errorhandling.entity;

import com.org.wortel.mastercardbin.application.errorhandling.general.BusinessException;
import jakarta.ws.rs.core.Response;

public class NonUniqueEntityException extends BusinessException {

    private static final String DEFAULT_MESSAGE = "Entity %s created conflict with given criteria. %s";
    private static final String GENERIC_MESSAGE = "Provided search criteria matched multiple entities.";

    protected NonUniqueEntityException(String entityName, String message) {
        super(String.format(DEFAULT_MESSAGE, entityName, message), GENERIC_MESSAGE, Response.Status.CONFLICT);
    }
}
