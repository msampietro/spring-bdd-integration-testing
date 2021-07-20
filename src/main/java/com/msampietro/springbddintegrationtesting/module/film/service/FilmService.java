package com.msampietro.springbddintegrationtesting.module.film.service;

import com.msampietro.springbddintegrationtesting.exception.ObjectNotFoundException;
import com.msampietro.springbddintegrationtesting.module.base.service.BaseService;
import com.msampietro.springbddintegrationtesting.module.film.model.Film;
import com.msampietro.springbddintegrationtesting.module.review.dto.ReviewDTO;
import com.msampietro.springbddintegrationtesting.module.review.hal.ReviewResource;

public interface FilmService extends BaseService<Film, Long> {

    ReviewResource addReviewToFilm(long filmId, ReviewDTO input) throws ObjectNotFoundException;

    ReviewResource removeReviewFromFilm(long filmId, long reviewId) throws ObjectNotFoundException;

}
