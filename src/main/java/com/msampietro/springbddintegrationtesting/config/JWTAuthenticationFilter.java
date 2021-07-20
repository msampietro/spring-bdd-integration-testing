package com.msampietro.springbddintegrationtesting.config;

import com.msampietro.springbddintegrationtesting.exception.ExceptionControllerAdvice;
import com.msampietro.springbddintegrationtesting.exception.InvalidTokenException;
import com.msampietro.springbddintegrationtesting.misc.ApplicationProperties;
import com.msampietro.springbddintegrationtesting.misc.SecurityUtils;
import com.msampietro.springbddintegrationtesting.misc.TokenUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.msampietro.springbddintegrationtesting.misc.ApplicationConstants.*;

@Log4j2
@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {

    private final ExceptionControllerAdvice exceptionControllerAdvice;
    private final ApplicationProperties applicationProperties;

    @Autowired
    JWTAuthenticationFilter(ExceptionControllerAdvice exceptionControllerAdvice,
                            ApplicationProperties applicationProperties) {
        this.exceptionControllerAdvice = exceptionControllerAdvice;
        this.applicationProperties = applicationProperties;
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public void doFilterInternal(HttpServletRequest request,
                                 HttpServletResponse response,
                                 FilterChain chain) throws IOException, ServletException {
        Optional<String> bearerToken = SecurityUtils.getBearerToken(request);
        if (bearerToken.isEmpty()) {
            chain.doFilter(request, response);
            return;
        }
        try {
            String jwt = bearerToken.orElse("");
            Map<String, Object> jwtValues = parseAndValidateJwt(jwt);
            Authentication authentication = getAuthentication(jwtValues);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            chain.doFilter(request, response);
        } catch (InvalidTokenException e) {
            sendError(response, exceptionControllerAdvice.handleInvalidTokenException(request, e));
        } finally {
            SecurityContextHolder.clearContext();
        }
    }

    public Map<String, Object> parseAndValidateJwt(String jwt) {
        validateAudience(jwt);
        return TokenUtils.parseJwt(jwt);
    }

    private Authentication getAuthentication(Map<String, Object> jwtValues) {
        return new JWTAuthenticationToken((String) jwtValues.get(JWT_CLAIM_USERNAME), getAuthorities(jwtValues));
    }

    @SuppressWarnings("unchecked")
    public List<GrantedAuthority> getAuthorities(Map<String, Object> jwtValues) {
        List<String> scopeList = (List<String>) jwtValues.get(JWT_CLAIM_SCOPE);
        List<GrantedAuthority> grantedAuthorities = scopeList.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        grantedAuthorities.add(new SimpleGrantedAuthority((String) jwtValues.get(JWT_CLAIM_ROLE)));
        return grantedAuthorities;
    }

    private void sendError(HttpServletResponse response, ResponseEntity<Object> responseEntity) throws IOException {
        String apiError = (String) responseEntity.getBody();
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(responseEntity.getStatusCodeValue());
        if (apiError != null)
            response.getWriter().write(apiError);
    }

    private void validateAudience(String jwt) {
        TokenUtils.validate(jwt, applicationProperties.getApiIdentifier(), applicationProperties.getActiveProfile());
    }

}
