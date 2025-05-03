package com.org.wortel.mastercardbin;

import com.org.wortel.mastercardbin.infrastructure.api.util.JwtTokenUtil;

import java.util.logging.Logger;

public class JwtGenerator {

    private static final Logger LOGGER = Logger.getLogger(JwtGenerator.class.getName());

    public static void main(String[] args) {
        String jwtToken = JwtTokenUtil.generateJwtToken();
        LOGGER.info("Generated JWT Token: " + jwtToken);
    }
}
