package com.msampietro.springbddintegrationtesting.module.review.projection;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.msampietro.springbddintegrationtesting.module.film.projection.FilmProjection;
import com.msampietro.springbddintegrationtesting.module.base.projection.BaseProjection;

public interface ReviewProjection extends BaseProjection<Long> {

    String getComment();

    String getUsername();

    interface Full extends ReviewProjection {

        @JsonIgnore
        FilmProjection getFilm();

    }

}