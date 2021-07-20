package com.msampietro.springbddintegrationtesting.module.review.service.impl;

import com.msampietro.springbddintegrationtesting.exception.ObjectNotFoundException;
import com.msampietro.springbddintegrationtesting.module.base.service.impl.BaseServiceImpl;
import com.msampietro.springbddintegrationtesting.module.film.model.Film;
import com.msampietro.springbddintegrationtesting.module.review.dto.ReviewDTO;
import com.msampietro.springbddintegrationtesting.module.review.hal.ReviewResource;
import com.msampietro.springbddintegrationtesting.module.review.model.Review;
import com.msampietro.springbddintegrationtesting.module.review.projection.ReviewProjection;
import com.msampietro.springbddintegrationtesting.module.review.repository.ReviewRepository;
import com.msampietro.springbddintegrationtesting.module.review.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static com.msampietro.springbddintegrationtesting.functional.ThrowingFunction.throwingFunction;

@Service
public class ReviewServiceImpl extends BaseServiceImpl<Review, Long> implements ReviewService {

    private final ReviewRepository reviewRepository;

    @Autowired
    public ReviewServiceImpl(ReviewRepository reviewRepository) {
        super(reviewRepository);
        this.reviewRepository = reviewRepository;
    }

    @Override
    public ReviewResource addReview(Film film, ReviewDTO input) throws ObjectNotFoundException {
        var newReview = this.getObjectMapper().convertValue(input, Review.class);
        newReview.setFilm(film);
        var result = reviewRepository.saveAndFlush(newReview);
        var projection = this.getProjectionFactory().createProjection(ReviewProjection.class, result);
        return new ReviewResource(projection);
    }

    @Override
    public ReviewResource removeReview(Film film, long reviewId) throws ObjectNotFoundException {
        var review = this.findEntityEagerlyById(reviewId);
        film.getReviews().remove(review);
        var projection = this.getProjectionFactory().createProjection(ReviewProjection.class, review);
        return new ReviewResource(projection);
    }

    @Override
    public Page<ReviewResource> findAll(long filmProductionCompanyId, Pageable pageable) {
        var pageResult = reviewRepository
                .findAllProjectedByFilmId(filmProductionCompanyId, pageable);
        return pageResult.map(throwingFunction(ReviewResource::new));
    }

}
