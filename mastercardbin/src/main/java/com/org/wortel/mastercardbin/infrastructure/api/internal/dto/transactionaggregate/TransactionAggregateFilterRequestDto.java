package com.org.wortel.mastercardbin.infrastructure.api.internal.dto.transactionaggregate;

import com.org.wortel.mastercardbin.infrastructure.api.internal.dto.transactionaggregate.util.DateBefore;
import jakarta.validation.constraints.Pattern;
import jakarta.ws.rs.QueryParam;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@DateBefore(from = "from", to = "to")
public class TransactionAggregateFilterRequestDto {

    @QueryParam("from")
    private LocalDateTime from;

    @QueryParam("to")
    private LocalDateTime to;

    @QueryParam("location")
    private String location;

    @QueryParam("binPrefix")
    @Pattern(regexp = "^$|^\\d{1,6}$", message = "BIN prefix must have maximum of 6 digits")
    private String binPrefix;
}
