package com.org.wortel.mastercardbin.mastercard;

import com.org.wortel.mastercardbin.application.bindata.auhorization.MastercardSigningKeyProvider;
import com.org.wortel.mastercardbin.application.bindata.util.MastercardProperties;
import com.org.wortel.mastercardbin.application.errorhandling.mastercard.MastercardAuthorizationException;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@QuarkusTest
class MastercardSigningKeyProviderTest {

    @Inject
    MastercardSigningKeyProvider mastercardSigningKeyProvider;

    @Test
    void successfullyGetSigningKey() {
        // given when
        var signingKey = mastercardSigningKeyProvider.getSigningKey();

        // then
        assertNotNull(signingKey);
    }

    @Test
    void throwExceptionWhenKeystorePathIsInvalid() {
        // given
        MastercardProperties mockMastercardProperties = mock(MastercardProperties.class);
        when(mockMastercardProperties.getKeystorePath()).thenReturn("invalid/path/to/keystore");
        when(mockMastercardProperties.getKeyAlias()).thenReturn("alias");
        when(mockMastercardProperties.getKeystorePassword()).thenReturn("password");

        var signingKeyProviderWithMockedProperties = new MastercardSigningKeyProvider(mockMastercardProperties);

        // when then
        assertThrows(MastercardAuthorizationException.class, signingKeyProviderWithMockedProperties::getSigningKey);
    }
}
