package com.org.wortel.mastercardbin.infrastructure.api.internal.dto.newtransaction;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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
public class NewTransactionRequestDto {

    @Pattern(regexp = "^\\d{6}(\\d{2})?$", message = "BIN number must be 6 or 8 digits")
    private String binNumber;

    @NotNull(message = "Amount must not be null")
    @DecimalMin(value = "0.01", message = "Amount must be greater than 0")
    private BigDecimal amount;

    @NotBlank(message = "Location must not be blank")
    private String location;

    @NotNull(message = "Timestamp must not be null")
    private LocalDateTime timestamp;
}