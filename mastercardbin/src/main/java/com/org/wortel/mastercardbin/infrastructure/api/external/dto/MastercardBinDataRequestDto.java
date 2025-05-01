package com.org.wortel.mastercardbin.infrastructure.api.external.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class MastercardBinDataRequestDto {

    @NotBlank(message = "accountRange must not be blank")
    private String accountRange;
}