package com.org.wortel.mastercardbin.infrastructure.api.external.dto;

import com.org.wortel.mastercardbin.infrastructure.api.external.dto.valueobject.BinDataCountryDto;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class MastercardBinDataResponseDto {
    private String binNum;
    private String customerName;
    private BinDataCountryDto country;
}
