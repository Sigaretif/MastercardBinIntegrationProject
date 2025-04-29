package com.org.wortel.mastercardbin.infrastructure.external.config;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.Getter;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
@Getter
public class MastercardProperties {

    @ConfigProperty(name = "mastercard.keystore-path")
    String keystorePath;
    @ConfigProperty(name = "mastercard.keystore-password")
    String keystorePassword;
    @ConfigProperty(name = "mastercard.key-alias")
    String keyAlias;
    @ConfigProperty(name = "mastercard.consumer-key")
    String consumerKey;
}
