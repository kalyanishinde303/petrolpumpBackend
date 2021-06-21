package com.petrol.security;

import static java.util.Objects.requireNonNull;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.petrol.model.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


@Service
public final class JwtServiceImpl implements JwtService {

    private final Environment env;

    public JwtServiceImpl(Environment env) {
        this.env = requireNonNull(env);
    }

    @Override
    public JwtToken createLoginToken(User user) {
    	
        return createJWT(
            UUID.randomUUID().toString(),
            env.getProperty("service.jwt.issuer"),
            String.valueOf(user.getEmail()),
            "admin",
            Long.parseLong(env.getProperty("service.jwt.expiration"))
        );
    }

    @Override
    public Claims validate(JwtToken token) {
        final Key signingKey = EncryptionUtil.getPublicKey(
            env.getProperty("service.jwt.public"));

        return Jwts.parser()
            .setSigningKey(signingKey)
            .parseClaimsJws(token.getToken())
            .getBody();
    }

    private JwtToken createJWT(String id,
                               String issuer,
                               String subject,
                               String privileges,
                               long ttlMillis) {

        // The JWT signature algorithm we will be using to sign the token
        final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.RS256;

        final long nowMillis = System.currentTimeMillis();
        final Date now = new Date(nowMillis);

        // We will sign our JWT with our ApiKey secret
        final Key signingKey = EncryptionUtil.getPrivateKey(
            env.getProperty("service.jwt.secret"));

        final Map<String, Object> claims = new HashMap<>();
        claims.put("privileges", privileges);

        // Let's set the JWT Claims
        final JwtBuilder builder = Jwts.builder()
            .setClaims(claims)
            .setId(id)
            .setIssuer(issuer)
            .setIssuedAt(now)
            .setSubject(subject)
            .signWith(signatureAlgorithm, signingKey);

        // If it has been specified, let's add the expiration
        if (ttlMillis >= 0) {
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }

        // Builds the JWT and serializes it to a compact, URL-safe string
        return new JwtToken(builder.compact());
    }
}
