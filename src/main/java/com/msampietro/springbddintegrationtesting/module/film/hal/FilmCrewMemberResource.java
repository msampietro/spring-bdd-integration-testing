package com.msampietro.springbddintegrationtesting.module.film.hal;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.msampietro.springbddintegrationtesting.exception.ObjectNotFoundException;
import com.msampietro.springbddintegrationtesting.module.film.controller.FilmCrewMemberController;
import com.msampietro.springbddintegrationtesting.module.film.projection.FilmCrewMemberProjection;
import org.springframework.hateoas.EntityModel;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class FilmCrewMemberResource extends EntityModel<FilmCrewMemberProjection> {

    private final FilmCrewMemberProjection filmCrewMemberProjection;

    public FilmCrewMemberResource(FilmCrewMemberProjection filmCrewMemberProjection) throws ObjectNotFoundException {
        this.filmCrewMemberProjection = filmCrewMemberProjection;
        add(linkTo(methodOn(FilmCrewMemberController.class).get(filmCrewMemberProjection.getId())).withSelfRel());
    }

    @JsonUnwrapped
    public FilmCrewMemberProjection getFilmCrewMemberProjection() {
        return this.filmCrewMemberProjection;
    }

}
