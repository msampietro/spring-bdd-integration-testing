package com.msampietro.springbddintegrationtesting.module.film.hal;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.msampietro.springbddintegrationtesting.exception.ObjectNotFoundException;
import com.msampietro.springbddintegrationtesting.module.actor.hal.ActorResource;
import com.msampietro.springbddintegrationtesting.module.actor.projection.ActorProjection;
import com.msampietro.springbddintegrationtesting.module.film.controller.FilmController;
import com.msampietro.springbddintegrationtesting.module.film.projection.FilmProjection;
import com.msampietro.springbddintegrationtesting.module.review.hal.ReviewResource;
import com.msampietro.springbddintegrationtesting.module.review.projection.ReviewProjection;
import org.springframework.hateoas.EntityModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class FilmResource extends EntityModel<FilmProjection> {

    private final FilmProjection filmProjection;
    private final FilmProductionCompanyResource filmProductionCompanyResource;
    private final Map<String, Object> embedded = new HashMap<>();

    public FilmResource(FilmProjection filmProjection) throws ObjectNotFoundException {
        this.filmProjection = filmProjection;
        this.filmProductionCompanyResource = new FilmProductionCompanyResource(filmProjection.getFilmProductionCompany());
        List<ActorResource> actorResources = new ArrayList<>();
        List<ReviewResource> reviewResources = new ArrayList<>();
        add(linkTo(methodOn(FilmController.class).get(filmProjection.getId())).withSelfRel());
        for (ActorProjection actorProjection : filmProjection.getActors())
            actorResources.add(new ActorResource(actorProjection));
        for (ReviewProjection reviewProjection : filmProjection.getReviews())
            reviewResources.add(new ReviewResource(reviewProjection));
        embedded.put("actors", actorResources);
        embedded.put("reviews", reviewResources);
    }

    @JsonProperty("_embedded")
    public Map<String, Object> getEmbedded() {
        return this.embedded;
    }

    @JsonProperty("filmProductionCompany")
    public FilmProductionCompanyResource getFilmProductionCompanyResource() {
        return this.filmProductionCompanyResource;
    }

    @JsonUnwrapped
    public FilmProjection getFilmProjection() {
        return this.filmProjection;
    }

}
