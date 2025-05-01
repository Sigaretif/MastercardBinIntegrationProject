package com.org.wortel.mastercardbin.infrastructure.api.util;

import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.util.Set;

@ApplicationScoped
public class JwtTokenUtil {

    @ConfigProperty(name = "mp.jwt.verify.issuer")
    String issuer;

    @ConfigProperty(name = "jwt.fixed-subject-name")
    String subject;

    public String generateJwtToken() {
        var timestamp = System.currentTimeMillis();
        return Jwt
                .issuer(issuer)
                .issuedAt(timestamp)
                .expiresAt(timestamp + 3600)
                .subject(subject)
                .groups(Set.of("USER"))
                .sign();
    }
}
