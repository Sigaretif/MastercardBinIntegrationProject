package com.org.wortel.mastercardbin.infrastructure.external.service;

import com.org.wortel.mastercardbin.infrastructure.external.api.client.MastercardClient;
import com.org.wortel.mastercardbin.infrastructure.external.api.client.MastercardClientFactory;
import com.org.wortel.mastercardbin.infrastructure.external.api.model.MastercardBinDataRequest;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
public class MastercardBinDataService {

    private final MastercardClient mastercardClient;

    @Inject
    public MastercardBinDataService(MastercardClientFactory mastercardClientFactory) {
        this.mastercardClient = mastercardClientFactory.createClient();
    }

    public Response getLookupBin(String binNumber) {
        var y = MastercardBinDataRequest.builder()
                .accountRange(binNumber)
                .build();
        Response response;
        try {
            response = mastercardClient.lookupBin(y);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get lookup bin", e);
        }
        return response;
    }
}
