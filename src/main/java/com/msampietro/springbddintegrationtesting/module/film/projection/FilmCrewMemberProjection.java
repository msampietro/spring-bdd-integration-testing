package com.msampietro.springbddintegrationtesting.module.film.projection;

import com.msampietro.springbddintegrationtesting.module.base.projection.BaseProjection;

public interface FilmCrewMemberProjection extends BaseProjection<Long> {

    String getName();

    String getLastName();

    FilmCrewJobProjection getFilmCrewJob();

    interface FilmCrewJobProjection extends BaseProjection<Long> {

        String getName();

    }

}
