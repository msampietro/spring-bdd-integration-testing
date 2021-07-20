package com.msampietro.springbddintegrationtesting.misc;

import com.msampietro.springbddintegrationtesting.exception.InvalidTokenException;
import org.apache.commons.lang3.StringUtils;

import java.security.InvalidParameterException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.msampietro.springbddintegrationtesting.misc.ApplicationConstants.*;

public final class TokenUtils {

    private static final String INVALID_TOKEN_ERROR_MESSAGE = "Invalid or Malformed JWT";

    private TokenUtils() {
    }

    public static List<String> getJwtScope(String jwt) {
        try {
            String rawScope = JWTParser.getClaim(jwt, JWT_CLAIM_SCOPE);
            String[] scopeList = StringUtils.split(rawScope, COMA);
            if (scopeList != null && scopeList.length > 0) {
                List<String> decoratedScopeList = new ArrayList<>();
                for (String scope : scopeList) {
                    if (StringUtils.startsWithIgnoreCase(scope, ALL)) {
                        Pattern pattern = Pattern.compile("(?<=:).*");
                        Matcher matcher = pattern.matcher(scope);
                        if (matcher.find()) {
                            String resource = matcher.group(0);
                            decoratedScopeList.add(StringUtils.join(READ, COLON, resource));
                            decoratedScopeList.add(StringUtils.join(CREATE, COLON, resource));
                            decoratedScopeList.add(StringUtils.join(UPDATE, COLON, resource));
                            decoratedScopeList.add(StringUtils.join(DELETE, COLON, resource));
                        }
                    } else
                        decoratedScopeList.add(scope);
                }
                decoratedScopeList.removeIf(item -> item == null || StringUtils.equals("", item));
                return decoratedScopeList;
            }
        } catch (InvalidParameterException e) {
            throw new InvalidTokenException(INVALID_TOKEN_ERROR_MESSAGE);
        }
        return Collections.emptyList();
    }

    public static Optional<String> getJwtRoleClaim(String jwt) {
        return getClaim(jwt, JWT_CLAIM_ROLE);
    }

    public static Optional<String> getJwtGroupClaim(String jwt) {
        return getClaim(jwt, JWT_CLAIM_GROUP);
    }

    public static Optional<String> getJwtUsernameClaim(String jwt) {
        return getClaim(jwt, JWT_CLAIM_USERNAME);
    }

    public static Optional<String> getJwtAudience(String jwt) {
        return getClaim(jwt, JWT_AUDIENCE);
    }

    public static Optional<String> getClaim(String jwt, String claim) {
        try {
            String claimResult = JWTParser.getClaim(jwt, claim);
            if (StringUtils.isNotBlank(claimResult))
                return Optional.of(claimResult);
        } catch (InvalidParameterException e) {
            throw new InvalidTokenException(INVALID_TOKEN_ERROR_MESSAGE);
        }
        return Optional.empty();
    }

    public static void validate(String jwt, String expectedAud, String expectedGroup) {
        String aud = TokenUtils.getJwtAudience(jwt)
                .orElseThrow(() -> new InvalidTokenException("Invalid token audience"));
        List<String> audList = Arrays.asList(StringUtils.split(aud, COMA));
        if (!audList.contains(expectedAud))
            throw new InvalidTokenException("Access not allowed for current aud");
        String group = TokenUtils.getJwtGroupClaim(jwt)
                .orElseThrow(() -> new InvalidTokenException("Invalid token group"));
        if (!StringUtils.equals(group, expectedGroup))
            throw new InvalidTokenException("Access not allowed for current group");
    }

    public static Map<String, Object> parseJwt(String jwt) {
        Map<String, Object> jwtValues = new HashMap<>();
        jwtValues.put(JWT_CLAIM_SCOPE, getJwtScope(jwt));
        jwtValues.put(JWT_CLAIM_USERNAME, getJwtUsernameClaim(jwt).orElseThrow(() -> new InvalidTokenException("Invalid JWT Username")));
        jwtValues.put(JWT_CLAIM_GROUP, getJwtGroupClaim(jwt).orElseThrow(() -> new InvalidTokenException("Invalid JWT Group")));
        String role = getJwtRoleClaim(jwt).orElseThrow(() -> new InvalidTokenException("Invalid JWT Role"));
        jwtValues.put(JWT_CLAIM_ROLE, StringUtils.join(ROLE_PREFIX, role.toUpperCase()));
        return jwtValues;
    }

}
