package com.msampietro.springbddintegrationtesting.module.review.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.msampietro.springbddintegrationtesting.exception.ObjectNotFoundException;
import com.msampietro.springbddintegrationtesting.module.base.controller.BaseControllerImpl;
import com.msampietro.springbddintegrationtesting.module.base.hal.PageModelWrapper;
import com.msampietro.springbddintegrationtesting.module.base.projection.BaseProjection;
import com.msampietro.springbddintegrationtesting.module.review.dto.ReviewDTO;
import com.msampietro.springbddintegrationtesting.module.review.model.Review;
import com.msampietro.springbddintegrationtesting.module.review.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/reviews")
public class ReviewController extends BaseControllerImpl<Review, Long, ReviewDTO> {

    @Autowired
    public ReviewController(ReviewService reviewService) {
        super(reviewService);
    }

    @Override
    @PostMapping
    public ResponseEntity<Object> create(@Valid @RequestBody ReviewDTO input) {
        return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
    }

    @Override
    @GetMapping(params = {"page", "size"}, produces = {"application/hal+json"})
    public ResponseEntity<PageModelWrapper<EntityModel<BaseProjection<Long>>>> getAll(@RequestParam int page, @RequestParam int size) {
        return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
    }

    @Override
    @DeleteMapping(value = "{objectId}")
    public ResponseEntity<JsonNode> delete(@PathVariable Long objectId) throws ObjectNotFoundException {
        return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
    }

}
