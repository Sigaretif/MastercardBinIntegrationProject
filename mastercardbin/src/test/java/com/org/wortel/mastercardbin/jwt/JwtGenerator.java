package com.org.wortel.mastercardbin.jwt;

import com.org.wortel.mastercardbin.infrastructure.api.util.JwtTokenUtil;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.util.logging.Logger;

@QuarkusTest
class JwtGenerator {

    private static final Logger LOGGER = Logger.getLogger(JwtGenerator.class.getName());

    @Inject
    JwtTokenUtil jwtTokenUtil;

    @Test
    void generateJwt() {
        String jwtToken = jwtTokenUtil.generateJwtToken();
        LOGGER.info("Generated JWT Token: " + jwtToken);
    }
}
