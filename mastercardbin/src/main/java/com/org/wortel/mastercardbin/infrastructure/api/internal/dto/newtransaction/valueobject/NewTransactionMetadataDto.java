package com.org.wortel.mastercardbin.infrastructure.api.internal.dto.newtransaction.valueobject;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NewTransactionMetadataDto {

    private Long id;
    private String customerName;
    private String countryName;
    private Integer countryCode;
}
