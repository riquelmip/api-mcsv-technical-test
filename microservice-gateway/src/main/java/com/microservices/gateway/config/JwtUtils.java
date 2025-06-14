package com.microservices.gateway.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.logging.Logger;

@Component
public class JwtUtils {
    Logger logger = Logger.getLogger(JwtUtils.class.getName());

    @Value("${security.jwt.key.private}")
    private String privateKey;

    @Value("${security.jwt.user.generator}")
    private String userGenerator;

    @Value("${security.jwt.expiration}")
    private int tokenExpiration;

//    public String createToken(Authentication authentication) {
//        Algorithm algorithm = Algorithm.HMAC256(this.privateKey);
//
//        String username = authentication.getPrincipal().toString();
//        String authorities = authentication.getAuthorities()
//                .stream()
//                .map(GrantedAuthority::getAuthority)
//                .collect(Collectors.joining(","));
//
//        return JWT.create()
//                .withIssuer(this.userGenerator)
//                .withSubject(username)
//                .withClaim("authorities", authorities)
//                .withIssuedAt(new Date())
//                .withExpiresAt(new Date(System.currentTimeMillis() + this.tokenExpiration))
//                .withJWTId(UUID.randomUUID().toString())
//                .withNotBefore(new Date(System.currentTimeMillis()))
//                .sign(algorithm);
//    }

    public DecodedJWT validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(this.privateKey);

            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(this.userGenerator)
                    .build();

            return verifier.verify(token);
        } catch (JWTVerificationException exception) {
            throw new JWTVerificationException("Token invalid, not Authorized");
        }
    }

    public String extractUsername(DecodedJWT decodedJWT) {
        return decodedJWT.getSubject().toString();
    }

    public Claim getSpecificClaim(DecodedJWT decodedJWT, String claimName) {
        return decodedJWT.getClaim(claimName);
    }

    public boolean isExpired(String token) {
        DecodedJWT decodedJWT = this.validateToken(token);
        Date expirationDate = decodedJWT.getExpiresAt();
        return expirationDate.before(new Date());
    }
}