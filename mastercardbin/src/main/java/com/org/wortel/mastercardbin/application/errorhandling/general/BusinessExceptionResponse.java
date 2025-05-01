package com.org.wortel.mastercardbin.application.errorhandling.general;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BusinessExceptionResponse {

    private String status;
    private Integer statusCode;
    private String message;
    private String genericMessage;

    public BusinessExceptionResponse(BusinessException exception) {
        this.status = exception.getStatus().getReasonPhrase();
        this.statusCode = exception.getStatus().getStatusCode();
        this.message = exception.getMessage();
        this.genericMessage = exception.getGenericMessage();
    }
}
