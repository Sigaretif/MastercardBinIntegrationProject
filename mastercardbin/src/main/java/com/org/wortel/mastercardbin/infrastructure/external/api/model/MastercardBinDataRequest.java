package com.org.wortel.mastercardbin.infrastructure.external.api.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class MastercardBinDataRequest {

    private String accountRange;
}