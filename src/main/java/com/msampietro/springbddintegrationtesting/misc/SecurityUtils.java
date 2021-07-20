package com.msampietro.springbddintegrationtesting.misc;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

import static com.msampietro.springbddintegrationtesting.misc.ApplicationConstants.*;

public class SecurityUtils {

    private SecurityUtils() {
    }

    public static Optional<String> getBearerToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.isNotBlank(authorizationHeader) && StringUtils.startsWithIgnoreCase(authorizationHeader, AUTHORIZATION_BEARER_PREFIX))
            return Optional.of(JWTParser.removeJwtPrefix(authorizationHeader));
        return Optional.empty();
    }

    public static String buildScope(String operation, String resource) {
        return String.join(COLON, operation.toLowerCase(), resource.toLowerCase());
    }

}
