package com.msampietro.springbddintegrationtesting.config;

import com.msampietro.springbddintegrationtesting.misc.SecurityUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.filter.ForwardedHeaderFilter;

import static com.msampietro.springbddintegrationtesting.misc.ApplicationConstants.*;
import static com.msampietro.springbddintegrationtesting.misc.ControllerEndpoints.*;


@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final JWTAuthenticationFilter authenticationFilter;
    public static final String ALL_PARAMS = "/**";

    public WebSecurityConfig(JWTAuthenticationFilter authenticationFilter) {
        this.authenticationFilter = authenticationFilter;
    }

    @Bean
    ForwardedHeaderFilter forwardedHeaderFilter() {
        return new ForwardedHeaderFilter();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().cors().and().authorizeRequests()
                //Films
                .antMatchers(HttpMethod.GET, StringUtils.join(FILMS_ENDPOINT, ALL_PARAMS)).hasAuthority(SecurityUtils.buildScope(READ, FILMS_RESOURCE))
                .antMatchers(HttpMethod.POST, StringUtils.join(FILMS_ENDPOINT, ALL_PARAMS)).hasAuthority(SecurityUtils.buildScope(CREATE, FILMS_RESOURCE))
                .antMatchers(HttpMethod.PUT, StringUtils.join(FILMS_ENDPOINT, ALL_PARAMS)).hasAuthority(SecurityUtils.buildScope(UPDATE, FILMS_RESOURCE))
                .antMatchers(HttpMethod.PATCH, StringUtils.join(FILMS_ENDPOINT, ALL_PARAMS)).hasAuthority(SecurityUtils.buildScope(UPDATE, FILMS_RESOURCE))
                .antMatchers(HttpMethod.DELETE, StringUtils.join(FILMS_ENDPOINT, ALL_PARAMS)).hasAuthority(SecurityUtils.buildScope(DELETE, FILMS_RESOURCE))
                //Films Production Company
                .antMatchers(HttpMethod.GET, StringUtils.join(FILM_PRODUCTION_COMPANIES_ENDPOINT, ALL_PARAMS)).hasAuthority(SecurityUtils.buildScope(READ, FILM_PRODUCTION_COMPANIES_RESOURCE))
                .antMatchers(HttpMethod.POST, StringUtils.join(FILM_PRODUCTION_COMPANIES_ENDPOINT, ALL_PARAMS)).hasAuthority(SecurityUtils.buildScope(CREATE, FILM_PRODUCTION_COMPANIES_RESOURCE))
                .antMatchers(HttpMethod.PUT, StringUtils.join(FILM_PRODUCTION_COMPANIES_ENDPOINT, ALL_PARAMS)).hasAuthority(SecurityUtils.buildScope(UPDATE, FILM_PRODUCTION_COMPANIES_RESOURCE))
                .antMatchers(HttpMethod.PATCH, StringUtils.join(FILM_PRODUCTION_COMPANIES_ENDPOINT, ALL_PARAMS)).hasAuthority(SecurityUtils.buildScope(UPDATE, FILM_PRODUCTION_COMPANIES_RESOURCE))
                .antMatchers(HttpMethod.DELETE, StringUtils.join(FILM_PRODUCTION_COMPANIES_ENDPOINT, ALL_PARAMS)).hasAuthority(SecurityUtils.buildScope(DELETE, FILM_PRODUCTION_COMPANIES_RESOURCE))
                //Films Crew Members
                .antMatchers(HttpMethod.GET, StringUtils.join(FILM_CREW_MEMBERS_ENDPOINT, ALL_PARAMS)).hasAuthority(SecurityUtils.buildScope(READ, FILM_CREW_MEMBERS_RESOURCE))
                .antMatchers(HttpMethod.PATCH, StringUtils.join(FILM_CREW_MEMBERS_ENDPOINT, ALL_PARAMS)).hasAuthority(SecurityUtils.buildScope(UPDATE, FILM_CREW_MEMBERS_RESOURCE))
                .antMatchers(HttpMethod.DELETE, StringUtils.join(FILM_CREW_MEMBERS_ENDPOINT, ALL_PARAMS)).hasAuthority(SecurityUtils.buildScope(DELETE, FILM_CREW_MEMBERS_RESOURCE))
                //Reviews
                .antMatchers(HttpMethod.GET, StringUtils.join(REVIEWS_ENDPOINT, ALL_PARAMS)).hasAuthority(SecurityUtils.buildScope(READ, REVIEWS_RESOURCE))
                .antMatchers(HttpMethod.PATCH, StringUtils.join(REVIEWS_ENDPOINT, ALL_PARAMS)).hasAuthority(SecurityUtils.buildScope(UPDATE, REVIEWS_RESOURCE))
                .antMatchers(HttpMethod.DELETE, StringUtils.join(REVIEWS_ENDPOINT, ALL_PARAMS)).hasAuthority(SecurityUtils.buildScope(DELETE, REVIEWS_RESOURCE))
                //Actors
                .antMatchers(HttpMethod.GET, StringUtils.join(ACTORS_ENDPOINT, ALL_PARAMS)).hasAuthority(SecurityUtils.buildScope(READ, ACTORS_RESOURCE))
                .antMatchers(HttpMethod.POST, StringUtils.join(ACTORS_ENDPOINT, ALL_PARAMS)).hasAuthority(SecurityUtils.buildScope(CREATE, ACTORS_RESOURCE))
                .antMatchers(HttpMethod.PATCH, StringUtils.join(ACTORS_ENDPOINT, ALL_PARAMS)).hasAuthority(SecurityUtils.buildScope(UPDATE, ACTORS_RESOURCE))
                .antMatchers(HttpMethod.DELETE, StringUtils.join(ACTORS_ENDPOINT, ALL_PARAMS)).hasAuthority(SecurityUtils.buildScope(DELETE, ACTORS_RESOURCE))
                //All
                .anyRequest().authenticated()
                .and()
                //Filters
                .addFilterBefore(authenticationFilter, BasicAuthenticationFilter.class)
                //Session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }
}
