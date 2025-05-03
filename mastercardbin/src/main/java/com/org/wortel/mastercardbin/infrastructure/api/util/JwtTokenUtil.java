package com.org.wortel.mastercardbin.infrastructure.api.util;

import io.smallrye.jwt.build.Jwt;
import lombok.experimental.UtilityClass;

import java.util.Set;

@UtilityClass
public class JwtTokenUtil {

    public String generateJwtToken() {
        var timestamp = System.currentTimeMillis();
        return Jwt
                .issuer("masercardbin-issuer")
                .issuedAt(timestamp)
                .expiresAt(timestamp + 3600)
                .subject("user")
                .groups(Set.of("USER"))
                .sign();
    }
}
