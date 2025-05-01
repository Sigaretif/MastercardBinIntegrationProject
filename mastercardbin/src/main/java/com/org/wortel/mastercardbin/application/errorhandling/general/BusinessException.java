package com.org.wortel.mastercardbin.application.errorhandling.general;

import jakarta.ws.rs.core.Response;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BusinessException extends RuntimeException{

    private final Response.Status status;
    private final String genericMessage;

    protected BusinessException(String message, String genericMessage, Response.Status status) {
        super(message);
        this.status = status;
        this.genericMessage = genericMessage;
    }
}
