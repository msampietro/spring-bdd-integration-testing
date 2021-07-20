package com.msampietro.springbddintegrationtesting.module.film.controller;

import com.msampietro.springbddintegrationtesting.exception.ObjectNotFoundException;
import com.msampietro.springbddintegrationtesting.module.base.controller.BaseControllerImpl;
import com.msampietro.springbddintegrationtesting.module.base.hal.PageModelWrapper;
import com.msampietro.springbddintegrationtesting.module.film.dto.FilmDTO;
import com.msampietro.springbddintegrationtesting.module.film.model.Film;
import com.msampietro.springbddintegrationtesting.module.film.service.FilmService;
import com.msampietro.springbddintegrationtesting.module.review.dto.ReviewDTO;
import com.msampietro.springbddintegrationtesting.module.review.hal.ReviewResource;
import com.msampietro.springbddintegrationtesting.module.review.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.msampietro.springbddintegrationtesting.misc.ControllerEndpoints.FILMS_ENDPOINT;

@RestController
@RequestMapping(value = FILMS_ENDPOINT)
public class FilmController extends BaseControllerImpl<Film, Long, FilmDTO> {

    private final FilmService filmService;
    private final ReviewService reviewService;

    @Autowired
    public FilmController(FilmService filmService,
                          ReviewService reviewService) {
        super(filmService);
        this.filmService = filmService;
        this.reviewService = reviewService;
    }

    @PutMapping(value = "{objectId}/reviews")
    public ResponseEntity<ReviewResource> addReviewToFilm(@PathVariable long objectId,
                                                          @Valid @RequestBody ReviewDTO reviewDTO) throws ObjectNotFoundException {
        return ResponseEntity.ok(filmService.addReviewToFilm(objectId, reviewDTO));
    }

    @DeleteMapping(value = "{objectId}/reviews/{reviewId}")
    public ResponseEntity<ReviewResource> removeReviewFromFilm(@PathVariable Long objectId, @PathVariable Long reviewId) throws ObjectNotFoundException {
        return ResponseEntity.ok(filmService.removeReviewFromFilm(objectId, reviewId));
    }

    @GetMapping(value = "{objectId}/reviews", params = {"page", "size"}, produces = {"application/hal+json"})
    public ResponseEntity<PageModelWrapper<ReviewResource>> getAllReviews(@PathVariable Long objectId,
                                                                          @RequestParam int page,
                                                                          @RequestParam int size) {
        var pageRequest = PageRequest.of(page - 1, size);
        var result = reviewService.findAll(objectId, pageRequest);
        return ResponseEntity.ok(new PageModelWrapper<>(result, pageRequest));
    }

}
