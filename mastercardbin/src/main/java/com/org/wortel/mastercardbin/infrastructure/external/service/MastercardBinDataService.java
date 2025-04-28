package com.org.wortel.mastercardbin.infrastructure.external.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mastercard.developer.oauth.OAuth;
import com.org.wortel.mastercardbin.infrastructure.external.config.MastercardProperties;
import com.org.wortel.mastercardbin.infrastructure.external.model.MastercardBinDataRequest;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.HttpMethod;
import lombok.RequiredArgsConstructor;

import java.net.URI;
import java.nio.charset.StandardCharsets;

@ApplicationScoped
@RequiredArgsConstructor
public class MastercardBinDataService {

    private final MastercardProperties mastercardProperties;
    private final MastercardSigningKeyProvider signingKeyProvider;

    private String getAuthorizationHeader() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return OAuth.getAuthorizationHeader(
                    new URI("https://sandbox.api.mastercard.com"),
                    HttpMethod.POST,
                    objectMapper.writeValueAsString(
                            MastercardBinDataRequest.builder()
                                    .accountRange("522169")
                                    .build()
                    ),
                    StandardCharsets.UTF_8,
                    mastercardProperties.consumerKey(),
                    signingKeyProvider.getSigningKey()
                    );
        }
        catch (Exception e) {
            throw new RuntimeException("Failed to get authorization header", e);
        }
    }
}
