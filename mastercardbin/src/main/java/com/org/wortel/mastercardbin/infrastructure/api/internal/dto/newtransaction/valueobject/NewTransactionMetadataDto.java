package com.org.wortel.mastercardbin.infrastructure.api.internal.dto.newtransaction.valueobject;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NewTransactionMetadataDto {

    private Long id;
    private String customerName;
    private String countryName;
    private Integer countryCode;
}
