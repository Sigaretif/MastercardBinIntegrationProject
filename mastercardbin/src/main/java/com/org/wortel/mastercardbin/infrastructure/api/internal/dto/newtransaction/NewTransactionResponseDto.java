package com.org.wortel.mastercardbin.infrastructure.api.internal.dto.newtransaction;

import com.org.wortel.mastercardbin.infrastructure.api.internal.dto.newtransaction.valueobject.NewTransactionMetadataDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NewTransactionResponseDto {
    private Long id;
    private String binNumber;
    private BigDecimal amount;
    private String location;
    private LocalDateTime timestamp;
    private NewTransactionMetadataDto metadata;
}
