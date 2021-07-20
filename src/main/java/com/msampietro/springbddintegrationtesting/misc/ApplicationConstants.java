package com.msampietro.springbddintegrationtesting.misc;

public final class ApplicationConstants {

    public static final String AUTHORIZATION_BEARER_PREFIX = "Bearer";
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String WHITESPACE = " ";
    public static final String COMA = ",";
    public static final String COLON = ":";
    public static final String READ = "read";
    public static final String CREATE = "create";
    public static final String UPDATE = "update";
    public static final String DELETE = "delete";
    public static final String ALL = "all";
    public static final String ROLE_PREFIX = "ROLE_";
    public static final String JWT_CLAIM_ROLE = "role";
    public static final String JWT_CLAIM_GROUP = "group";
    public static final String JWT_CLAIM_USERNAME = "username";
    public static final String JWT_CLAIM_SCOPE = "scope";
    public static final String JWT_AUDIENCE = "aud";

    public static final String FILMS_RESOURCE = "films";
    public static final String FILM_PRODUCTION_COMPANIES_RESOURCE = "film_production_companies";
    public static final String FILM_CREW_MEMBERS_RESOURCE = "film_crew_members";
    public static final String ACTORS_RESOURCE = "actors";
    public static final String REVIEWS_RESOURCE = "reviews";

    private ApplicationConstants() {

    }

}
