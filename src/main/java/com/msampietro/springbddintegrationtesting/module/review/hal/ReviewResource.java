package com.msampietro.springbddintegrationtesting.module.review.hal;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.msampietro.springbddintegrationtesting.exception.ObjectNotFoundException;
import com.msampietro.springbddintegrationtesting.module.film.controller.FilmController;
import com.msampietro.springbddintegrationtesting.module.film.projection.FilmProjection;
import com.msampietro.springbddintegrationtesting.module.review.controller.ReviewController;
import com.msampietro.springbddintegrationtesting.module.review.projection.ReviewProjection;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class ReviewResource extends EntityModel<ReviewProjection> {

    private final ReviewProjection reviewProjection;
    private EntityModel<FilmProjection> filmResource;

    public ReviewResource(ReviewProjection reviewProjection) throws ObjectNotFoundException {
        this.reviewProjection = reviewProjection;
        add(WebMvcLinkBuilder.linkTo(methodOn(ReviewController.class).get(reviewProjection.getId())).withSelfRel());
    }

    public ReviewResource(ReviewProjection.Full fullReviewProjection) throws ObjectNotFoundException {
        this.reviewProjection = fullReviewProjection;
        add(WebMvcLinkBuilder.linkTo(methodOn(ReviewController.class).get(reviewProjection.getId())).withSelfRel());
        filmResource = EntityModel.of(fullReviewProjection.getFilm());
        filmResource.add(WebMvcLinkBuilder.linkTo(methodOn(FilmController.class).get(fullReviewProjection.getFilm().getId())).withSelfRel());
    }

    @JsonUnwrapped
    public ReviewProjection getReviewProjection() {
        return this.reviewProjection;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("film")
    public EntityModel<FilmProjection> getFilmResource() {
        return this.filmResource;
    }

}
