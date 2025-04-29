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
    private PrivateKey signingkey;

    public PrivateKey getSigningKey() {
        if (signingkey != null) {
            return signingkey;
        }
        try {
            signingkey = AuthenticationUtils.loadSigningKey(
                    mastercardProperties.getKeystorePath(),
                    mastercardProperties.getKeyAlias(),
                    mastercardProperties.getKeystorePassword());
            return signingkey;
        } catch (Exception e) {
            throw new RuntimeException("Failed to load signing key", e);
        }
    }
}