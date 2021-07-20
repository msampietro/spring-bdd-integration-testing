package com.msampietro.springbddintegrationtesting.module.film.service.impl;

import com.msampietro.springbddintegrationtesting.exception.ObjectNotFoundException;
import com.msampietro.springbddintegrationtesting.module.base.service.impl.BaseServiceImpl;
import com.msampietro.springbddintegrationtesting.module.film.model.Film;
import com.msampietro.springbddintegrationtesting.module.film.repository.FilmRepository;
import com.msampietro.springbddintegrationtesting.module.film.service.FilmService;
import com.msampietro.springbddintegrationtesting.module.review.dto.ReviewDTO;
import com.msampietro.springbddintegrationtesting.module.review.hal.ReviewResource;
import com.msampietro.springbddintegrationtesting.module.review.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityGraph;

@Service
public class FilmServiceImpl extends BaseServiceImpl<Film, Long> implements FilmService {

    private final ReviewService reviewService;

    @Autowired
    public FilmServiceImpl(FilmRepository filmRepository,
                           ReviewService reviewService) {
        super(filmRepository);
        this.reviewService = reviewService;
    }

    @Transactional
    @Override
    public ReviewResource addReviewToFilm(long filmId, ReviewDTO input) throws ObjectNotFoundException {
        var foundFilm = this.findEntityById(filmId);
        return reviewService.addReview(foundFilm, input);
    }

    @Transactional
    @Override
    public ReviewResource removeReviewFromFilm(long filmId, long reviewId) throws ObjectNotFoundException {
        var foundFilm = this.findEntityById(filmId);
        return reviewService.removeReview(foundFilm, reviewId);
    }

    @Override
    public EntityGraph<Film> buildEagerlyFetchEntityGraph() {
        var entityGraph = this.getEntityManager().createEntityGraph(Film.class);
        entityGraph.addSubgraph("filmGenre");
        entityGraph.addSubgraph("filmProductionCompany");
        return entityGraph;
    }
}
