package com.org.wortel.mastercardbin.infrastructure.api.external.client;

import com.org.wortel.mastercardbin.application.bindata.util.MastercardProperties;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;
import org.eclipse.microprofile.rest.client.RestClientBuilder;

import java.net.URI;

@ApplicationScoped
@RequiredArgsConstructor
public class MastercardClientFactory {

    private final MastercardProperties mastercardProperties;

    public MastercardClient createClient() {
        return RestClientBuilder.newBuilder()
                .baseUri(URI.create(mastercardProperties.getBaseUrl()))
                .register(MastercardClientRequestFilter.class)
                .build(MastercardClient.class);
    }
}
