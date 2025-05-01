package com.org.wortel.mastercardbin.application.bindata.auhorization;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mastercard.developer.oauth.OAuth;
import com.org.wortel.mastercardbin.application.bindata.util.MastercardProperties;
import com.org.wortel.mastercardbin.application.errorhandling.mastercard.MastercardAuthorizationException;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;

import java.net.URI;
import java.nio.charset.StandardCharsets;

@ApplicationScoped
@RequiredArgsConstructor
public class MastercardAuthorizationHeaderGenerator {

    private final MastercardSigningKeyProvider signingKeyProvider;
    private final MastercardProperties mastercardProperties;

    public String getAuthorizationHeader2(URI uri, String httpMethod, Object payload) {
        try {
            return OAuth.getAuthorizationHeader(
                    uri,
                    httpMethod,
                    new ObjectMapper().writeValueAsString(payload),
                    StandardCharsets.UTF_8,
                    mastercardProperties.getConsumerKey(),
                    signingKeyProvider.getSigningKey()
            );
        }
        catch (Exception e) {
            throw new MastercardAuthorizationException("Failed to generate authorization header", e.getMessage());
        }
    }
}
