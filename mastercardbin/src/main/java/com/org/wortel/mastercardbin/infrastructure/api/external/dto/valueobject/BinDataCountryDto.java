package com.org.wortel.mastercardbin.infrastructure.api.external.dto.valueobject;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class BinDataCountryDto {

    private String name;
    private Integer code;
}