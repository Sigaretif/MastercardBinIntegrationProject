package com.org.wortel.mastercardbin.infrastructure.api.external.dto;

import com.org.wortel.mastercardbin.infrastructure.api.external.dto.valueobject.BinDataCountryDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class MastercardBinDataResponseDto {
    private String binNum;
    private String customerName;
    private BinDataCountryDto country;
}
