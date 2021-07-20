package com.msampietro.springbddintegrationtesting;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.msampietro.springbddintegrationtesting.misc.ApplicationProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

@Component
public class AuthUtils {

    private final ApplicationProperties applicationProperties;

    @Autowired
    public AuthUtils(ApplicationProperties applicationProperties) {
        this.applicationProperties = applicationProperties;
    }

    public String obtainAccessToken(Map<String, String> claims) throws Exception {
        JWTCreator.Builder jwtBuilder = JWT.create().withIssuer("issuer")
                .withSubject(UUID.randomUUID().toString())
                .withKeyId("test_kid")
                .withAudience(applicationProperties.getApiIdentifier())
                .withExpiresAt(new Date(System.currentTimeMillis() + (long) 3600 * 1000L))
                .withIssuedAt(new Date());
        claims.forEach(jwtBuilder::withClaim);
        jwtBuilder.withClaim("group", applicationProperties.getActiveProfile());
        Algorithm algorithm = getSigningAlgorithm();
        return jwtBuilder.sign(algorithm);
    }

    private Algorithm getSigningAlgorithm() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        return Algorithm.RSA256(publicKey, privateKey);
    }

}
