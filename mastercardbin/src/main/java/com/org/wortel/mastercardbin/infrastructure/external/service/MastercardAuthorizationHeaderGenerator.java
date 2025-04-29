package com.org.wortel.mastercardbin.infrastructure.external.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mastercard.developer.oauth.OAuth;
import com.org.wortel.mastercardbin.infrastructure.external.api.model.MastercardBinDataRequest;
import com.org.wortel.mastercardbin.infrastructure.external.config.MastercardProperties;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.HttpMethod;

import java.net.URI;
import java.nio.charset.StandardCharsets;

@ApplicationScoped
public class MastercardAuthorizationHeaderGenerator {

    @Inject
    MastercardSigningKeyProvider signingKeyProvider;

    @Inject
    MastercardProperties mastercardProperties;

    public String getAuthorizationHeader(MastercardBinDataRequest request) {
        ObjectMapper objectMapper = new ObjectMapper();
        var signingKey = signingKeyProvider.getSigningKey();
        try {
            return OAuth.getAuthorizationHeader(
                    new URI("https://sandbox.api.mastercard.com/bin-resources/bin-ranges/account-searches"),
                    HttpMethod.POST,
                    objectMapper.writeValueAsString(request),
                    StandardCharsets.UTF_8,
                    mastercardProperties.getConsumerKey(),
                    signingKey
            );
        } catch (Exception e) {
            throw new RuntimeException("Failed to get authorization header", e);
        }
    }
}
