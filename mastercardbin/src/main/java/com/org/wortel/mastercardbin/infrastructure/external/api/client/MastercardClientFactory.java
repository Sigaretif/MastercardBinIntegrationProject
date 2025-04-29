package com.org.wortel.mastercardbin.infrastructure.external.api.client;

import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.rest.client.RestClientBuilder;

import java.net.URI;

@ApplicationScoped
public class MastercardClientFactory {

    public MastercardClient createClient() {
        try {
            return RestClientBuilder.newBuilder()
                    .baseUri(URI.create("https://sandbox.api.mastercard.com/bin-resources"))
                    .register(Interceptor.class)
                    .build(MastercardClient.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create MastercardClient", e);
        }
    }
}
