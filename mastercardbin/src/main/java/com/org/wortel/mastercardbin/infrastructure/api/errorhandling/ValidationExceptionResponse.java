package com.org.wortel.mastercardbin.infrastructure.api.errorhandling;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ValidationExceptionResponse {
    private String errorMessage;
    private Integer statusCode;
    private String status;
    private List<String> details;
}
