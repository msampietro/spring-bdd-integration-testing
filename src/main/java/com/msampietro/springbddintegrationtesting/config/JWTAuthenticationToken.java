package com.msampietro.springbddintegrationtesting.config;

import lombok.Getter;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.Collection;

@Getter
public class JWTAuthenticationToken extends AbstractAuthenticationToken implements Serializable {

    private static final long serialVersionUID = 141278127489124L;

    private final String principal;

    public JWTAuthenticationToken(@NonNull String principal,
                                  Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        if (authorities != null && !authorities.isEmpty())
            super.setAuthenticated(true);
        this.principal = principal;
    }

    public Object getCredentials() {
        return null;
    }

}
