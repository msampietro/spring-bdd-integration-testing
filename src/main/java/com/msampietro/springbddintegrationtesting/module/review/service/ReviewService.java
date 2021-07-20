package com.msampietro.springbddintegrationtesting.module.review.service;


import com.msampietro.springbddintegrationtesting.exception.ObjectNotFoundException;
import com.msampietro.springbddintegrationtesting.module.base.service.BaseService;
import com.msampietro.springbddintegrationtesting.module.film.model.Film;
import com.msampietro.springbddintegrationtesting.module.review.dto.ReviewDTO;
import com.msampietro.springbddintegrationtesting.module.review.hal.ReviewResource;
import com.msampietro.springbddintegrationtesting.module.review.model.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReviewService extends BaseService<Review, Long> {

    ReviewResource addReview(Film film, ReviewDTO input) throws ObjectNotFoundException;

    ReviewResource removeReview(Film film, long reviewId) throws ObjectNotFoundException;

    Page<ReviewResource> findAll(long filmId, Pageable pageable);

}
