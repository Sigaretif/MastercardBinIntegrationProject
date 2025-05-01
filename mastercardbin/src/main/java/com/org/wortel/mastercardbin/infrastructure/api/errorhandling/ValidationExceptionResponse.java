package com.org.wortel.mastercardbin.infrastructure.api.errorhandling;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class ValidationExceptionResponse {
    private String errorMessage;
    private Integer statusCode;
    private String status;
    private List<String> details;
}
