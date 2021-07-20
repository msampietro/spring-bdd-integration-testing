package com.msampietro.springbddintegrationtesting.misc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;

import java.nio.charset.StandardCharsets;
import java.security.InvalidParameterException;
import java.util.Base64;

import static com.msampietro.springbddintegrationtesting.misc.ApplicationConstants.AUTHORIZATION_BEARER_PREFIX;
import static com.msampietro.springbddintegrationtesting.misc.ApplicationConstants.WHITESPACE;
import static java.util.Optional.ofNullable;
import static org.apache.commons.lang3.StringUtils.removeStartIgnoreCase;

final class JWTParser {
    private static final int PAYLOAD = 1;
    private static final int JWT_PARTS = 3;

    private static final String INVALID_ERROR_MESSAGE = "Invalid JWT";
    private static final String PARSING_ERROR_MESSAGE = "Error parsing JWT";

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private JWTParser() {
    }

    /**
     * Returns payload of a JWT as a JSON object.
     *
     * @param jwt REQUIRED: valid JSON Web Token as String.
     * @return payload as a JSONObject.
     */
    private static JsonNode getPayload(String jwt) {
        try {
            validateJWT(jwt);
            Base64.Decoder dec = Base64.getDecoder();
            final String payload = jwt.split("\\.")[PAYLOAD];
            final byte[] sectionDecoded = dec.decode(payload);
            final String jwtSection = new String(sectionDecoded, StandardCharsets.UTF_8);
            return OBJECT_MAPPER.readTree(jwtSection);
        } catch (JsonProcessingException e) {
            throw new InvalidParameterException(PARSING_ERROR_MESSAGE);
        }
    }

    /**
     * Returns a claim, from the {@code JWT}s' payload, as a String.
     *
     * @param jwt   REQUIRED: valid JSON Web Token as String.
     * @param claim REQUIRED: claim name as String.
     * @return claim from the JWT as a String.
     */
    static String getClaim(String jwt, String claim) {
        try {
            String parsedJwt = removeJwtPrefix(jwt);
            final JsonNode payload = getPayload(parsedJwt);
            final JsonNode claimValue = payload.get(claim);

            if (claimValue != null) {
                return claimValue.asText();
            }
        } catch (final Exception e) {
            throw new InvalidParameterException(INVALID_ERROR_MESSAGE);
        }
        return "";
    }

    /**
     * Removes "Bearer or bearer" prefix from Authorization Header.
     *
     * @param authorizationHeader REQUIRED: valid Authorization header as String.
     * @return cleaned JWT as a String.
     */
    static String removeJwtPrefix(String authorizationHeader) {
        return ofNullable(authorizationHeader)
                .map(value -> removeStartIgnoreCase(value, StringUtils.join(AUTHORIZATION_BEARER_PREFIX, WHITESPACE)))
                .orElseThrow(() -> new InvalidParameterException(INVALID_ERROR_MESSAGE));
    }

    /**
     * Checks if {@code JWT} is a valid JSON Web Token.
     *
     * @param jwt REQUIRED: The JWT as a {@link String}.
     */
    private static void validateJWT(String jwt) {
        // Check if the the JWT has the three parts
        final String[] jwtParts = jwt.split("\\.");
        if (jwtParts.length != JWT_PARTS) {
            throw new InvalidParameterException("Malformed JWT");
        }
    }

}
