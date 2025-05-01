package com.org.wortel.mastercardbin.application.errorhandling.entity;

import com.org.wortel.mastercardbin.application.errorhandling.general.BusinessException;
import jakarta.ws.rs.core.Response;

public class EntityNotFoundException extends BusinessException {

    private static final String DEFAULT_MESSAGE = "Entity %s not found.";
    private static final String GENERIC_MESSAGE = "Provided search criteria did not match any entity.";

    public EntityNotFoundException(String entityName) {
        super(String.format(DEFAULT_MESSAGE, entityName), GENERIC_MESSAGE, Response.Status.NOT_FOUND);
    }
}
