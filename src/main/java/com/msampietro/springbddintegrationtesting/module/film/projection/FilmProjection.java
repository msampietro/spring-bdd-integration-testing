package com.msampietro.springbddintegrationtesting.module.film.projection;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.msampietro.springbddintegrationtesting.module.actor.projection.ActorProjection;
import com.msampietro.springbddintegrationtesting.module.base.projection.BaseProjection;
import com.msampietro.springbddintegrationtesting.module.review.projection.ReviewProjection;

import java.util.List;

public interface FilmProjection extends BaseProjection<Long> {

    String getName();

    Integer getRanking();

    @JsonIgnore
    FilmProductionCompanyProjection.Reduced getFilmProductionCompany();

    @JsonIgnore
    List<ActorProjection> getActors();

    @JsonIgnore
    List<ReviewProjection> getReviews();

}