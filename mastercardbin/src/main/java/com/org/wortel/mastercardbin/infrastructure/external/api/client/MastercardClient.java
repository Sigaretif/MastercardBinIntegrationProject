package com.org.wortel.mastercardbin.infrastructure.external.api.client;

import com.org.wortel.mastercardbin.infrastructure.external.api.model.MastercardBinDataRequest;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

public interface MastercardClient {

    @POST
    @Path("/bin-ranges/account-searches")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    Response lookupBin(MastercardBinDataRequest request);
}