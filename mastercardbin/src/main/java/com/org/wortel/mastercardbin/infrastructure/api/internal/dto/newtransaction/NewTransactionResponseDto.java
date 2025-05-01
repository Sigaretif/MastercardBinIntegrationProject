package com.org.wortel.mastercardbin.infrastructure.api.internal.dto.newtransaction;

import com.org.wortel.mastercardbin.infrastructure.api.internal.dto.newtransaction.valueobject.NewTransactionMetadataDto;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class NewTransactionResponseDto {
    private Long id;
    private String binNumber;
    private BigDecimal amount;
    private String location;
    private LocalDateTime timestamp;
    private NewTransactionMetadataDto metadata;
}
