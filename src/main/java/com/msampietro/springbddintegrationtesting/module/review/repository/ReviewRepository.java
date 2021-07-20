package com.msampietro.springbddintegrationtesting.module.review.repository;

import com.msampietro.springbddintegrationtesting.module.base.repository.ExtendedJpaRepository;
import com.msampietro.springbddintegrationtesting.module.review.model.Review;
import com.msampietro.springbddintegrationtesting.module.review.projection.ReviewProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends ExtendedJpaRepository<Review, Long> {

    Page<ReviewProjection> findAllProjectedByFilmId(long filmId, Pageable pageable);

}
