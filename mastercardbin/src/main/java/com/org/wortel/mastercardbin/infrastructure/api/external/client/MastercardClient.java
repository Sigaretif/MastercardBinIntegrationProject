package com.org.wortel.mastercardbin.infrastructure.api.external.client;

import com.org.wortel.mastercardbin.infrastructure.api.external.dto.MastercardBinDataRequestDto;
import com.org.wortel.mastercardbin.infrastructure.api.external.dto.MastercardBinDataResponseDto;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

public interface MastercardClient {

    @POST
    @Path("/bin-ranges/account-searches")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    List<MastercardBinDataResponseDto> getBinData(@Valid MastercardBinDataRequestDto request);
}