package com.msampietro.springbddintegrationtesting.module.film.projection;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.msampietro.springbddintegrationtesting.module.base.projection.BaseProjection;

import java.util.List;

public interface FilmProductionCompanyProjection extends BaseProjection<Long> {

    String getName();

    @JsonIgnore
    List<FilmCrewMemberProjection> getFilmCrewMembers();

    interface Reduced extends BaseProjection<Long> {

        String getName();

    }

}
