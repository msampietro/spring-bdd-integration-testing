create extension if not exists "uuid-ossp";

create sequence if not exists actor_seq;

create sequence if not exists film_crew_member_seq;

create sequence if not exists film_production_company_seq;

create sequence if not exists film_seq;

create sequence if not exists review_seq;

create table if not exists actor
(
    id             bigint                     not null
        constraint actor_pkey
            primary key,
    created_at     timestamp,
    last_name      varchar(255)               not null,
    name           varchar(255)               not null,
    deletion_token uuid    default uuid_nil() not null,
    modified_at    timestamp,
    deleted        boolean default false      not null
);

create table if not exists film_genre
(
    id             bigint                     not null
        constraint film_genre_pkey
            primary key,
    created_at     timestamp,
    name           varchar(255)               not null,
    deletion_token uuid    default uuid_nil() not null,
    modified_at    timestamp,
    deleted        boolean default false      not null
);

create table if not exists film_production_company
(
    id             bigint                     not null
        constraint film_production_company_pkey
            primary key,
    created_at     timestamp,
    name           varchar(255)               not null,
    deletion_token uuid    default uuid_nil() not null,
    modified_at    timestamp,
    deleted        boolean default false      not null
);

create table if not exists film
(
    id                         bigint                     not null
        constraint film_pkey
            primary key,
    created_at                 timestamp,
    name                       varchar(255)               not null,
    ranking                    integer                    not null,
    film_genre_id              bigint
        constraint fkfay5ajatwfb5j07059bj14q4d
            references film_genre,
    film_production_company_id bigint
        constraint fkfx1nbi00md3atdahtdgx0jg84
            references film_production_company,
    deletion_token             uuid    default uuid_nil() not null,
    modified_at                timestamp,
    deleted                    boolean default false      not null
);

create table if not exists film_actor
(
    film_id  bigint not null
        constraint fk44uk58x166xx1qd03300206nr
            references film,
    actor_id bigint not null
        constraint fksr7lo9p4intei645cws4f9t4l
            references actor
);

create table if not exists review
(
    id             bigint                     not null
        constraint review_pkey
            primary key,
    created_at     timestamp,
    comment        varchar(255)               not null,
    username       varchar(255)               not null,
    film_id        bigint                     not null
        constraint fkloj6cgl63e2nd5ykk8gvlu15b
            references film,
    deletion_token uuid    default uuid_nil() not null,
    modified_at    timestamp,
    deleted        boolean default false      not null
);

create table if not exists film_crew_job
(
    id             bigint                     not null
        constraint film_crew_job_pkey
            primary key,
    created_at     timestamp,
    name           varchar(255)               not null,
    deletion_token uuid    default uuid_nil() not null,
    modified_at    timestamp,
    deleted        boolean default false      not null
);

create table if not exists film_crew_member
(
    id                         bigint                     not null
        constraint film_crew_member_pkey
            primary key,
    created_at                 timestamp,
    last_name                  varchar(255)               not null,
    name                       varchar(255)               not null,
    film_production_company_id bigint                     not null
        constraint fkn3cab9krrc535bvlud53918sp
            references film_production_company,
    film_crew_job_id           bigint
        constraint fk25nlrg2ck0sh9t0ejp8aq515c
            references film_crew_job,
    deletion_token             uuid    default uuid_nil() not null,
    modified_at                timestamp,
    deleted                    boolean default false      not null
);