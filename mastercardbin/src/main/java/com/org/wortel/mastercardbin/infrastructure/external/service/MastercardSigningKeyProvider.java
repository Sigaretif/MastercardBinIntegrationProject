package com.org.wortel.mastercardbin.infrastructure.external.service;

import com.mastercard.developer.utils.AuthenticationUtils;
import com.org.wortel.mastercardbin.infrastructure.external.config.MastercardProperties;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;

import java.security.PrivateKey;

@ApplicationScoped
@RequiredArgsConstructor
public class MastercardSigningKeyProvider {

    private final MastercardProperties mastercardProperties;

    public PrivateKey getSigningKey() {
        try {
            return AuthenticationUtils.loadSigningKey(
                    mastercardProperties.keystorePath(),
                    mastercardProperties.keyAlias(),
                    mastercardProperties.keystorePassword());
        } catch (Exception e) {
            throw new RuntimeException("Failed to load signing key", e);
        }
    }
}