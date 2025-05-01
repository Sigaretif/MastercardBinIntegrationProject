package com.org.wortel.mastercardbin.infrastructure.api.external.dto.valueobject;

import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class BinDataCountryDto {

    private String name;
    private Integer code;
}