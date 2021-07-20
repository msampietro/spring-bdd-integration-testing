package com.msampietro.springbddintegrationtesting.module.film.hal;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.msampietro.springbddintegrationtesting.exception.ObjectNotFoundException;
import com.msampietro.springbddintegrationtesting.module.film.controller.FilmProductionCompanyController;
import com.msampietro.springbddintegrationtesting.module.film.projection.FilmCrewMemberProjection;
import com.msampietro.springbddintegrationtesting.module.film.projection.FilmProductionCompanyProjection;
import org.springframework.hateoas.EntityModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class FilmProductionCompanyResource extends EntityModel<FilmProductionCompanyProjection> {

    private FilmProductionCompanyProjection filmProductionCompanyProjection;
    private FilmProductionCompanyProjection.Reduced reducedFilmProductionCompanyProjection;
    private Map<String, Object> embedded;

    public FilmProductionCompanyResource(FilmProductionCompanyProjection filmProductionCompanyProjection) throws ObjectNotFoundException {
        this.filmProductionCompanyProjection = filmProductionCompanyProjection;
        this.embedded = new HashMap<>();
        add(linkTo(methodOn(FilmProductionCompanyController.class).get(filmProductionCompanyProjection.getId())).withSelfRel());
        List<FilmCrewMemberResource> filmCrewMemberResources = new ArrayList<>();
        for (FilmCrewMemberProjection filmCrewMemberProjection : filmProductionCompanyProjection.getFilmCrewMembers())
            filmCrewMemberResources.add(new FilmCrewMemberResource(filmCrewMemberProjection));
        embedded.put("filmCrewMembers", filmCrewMemberResources);
    }

    public FilmProductionCompanyResource(FilmProductionCompanyProjection.Reduced reducedFilmProductionCompanyProjection) throws ObjectNotFoundException {
        this.reducedFilmProductionCompanyProjection = reducedFilmProductionCompanyProjection;
        add(linkTo(methodOn(FilmProductionCompanyController.class).get(reducedFilmProductionCompanyProjection.getId())).withSelfRel());
    }

    @JsonProperty("_embedded")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Map<String, Object> getEmbedded() {
        return this.embedded;
    }

    @JsonUnwrapped
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public FilmProductionCompanyProjection getFilmProductionCompanyProjection() {
        return this.filmProductionCompanyProjection;
    }

    @JsonUnwrapped
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public FilmProductionCompanyProjection.Reduced getReducedFilmProductionCompanyProjection() {
        return this.reducedFilmProductionCompanyProjection;
    }

}
