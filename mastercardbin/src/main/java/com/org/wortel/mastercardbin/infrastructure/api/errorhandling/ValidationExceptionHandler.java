package com.org.wortel.mastercardbin.infrastructure.api.errorhandling;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class ValidationExceptionHandler implements ExceptionMapper<ConstraintViolationException> {

    private static final String ERROR_MESSAGE = "There were issues with provided data";

    @Override
    public Response toResponse(ConstraintViolationException e) {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(ValidationExceptionResponse.builder()
                        .errorMessage(ERROR_MESSAGE)
                        .status(Response.Status.BAD_REQUEST.getReasonPhrase())
                        .statusCode(Response.Status.BAD_REQUEST.getStatusCode())
                        .details(e.getConstraintViolations().stream()
                                .map(ConstraintViolation::getMessage)
                                .toList())
                        .build())
                .build();
    }
}
