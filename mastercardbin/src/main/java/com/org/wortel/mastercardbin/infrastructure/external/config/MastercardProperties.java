package com.org.wortel.mastercardbin.infrastructure.external.config;

import io.smallrye.config.ConfigMapping;

@ConfigMapping(prefix = "mastercard")
public interface MastercardProperties {

    String keystorePath();
    String keystorePassword();
    String keyAlias();
    String consumerKey();
}
