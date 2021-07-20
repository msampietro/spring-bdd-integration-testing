package com.msampietro.springbddintegrationtesting.module.film.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.msampietro.springbddintegrationtesting.exception.ObjectNotFoundException;
import com.msampietro.springbddintegrationtesting.module.base.controller.BaseControllerImpl;
import com.msampietro.springbddintegrationtesting.module.base.hal.PageModelWrapper;
import com.msampietro.springbddintegrationtesting.module.base.projection.BaseProjection;
import com.msampietro.springbddintegrationtesting.module.film.dto.FilmCrewMemberDTO;
import com.msampietro.springbddintegrationtesting.module.film.model.FilmCrewMember;
import com.msampietro.springbddintegrationtesting.module.film.service.FilmCrewMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.msampietro.springbddintegrationtesting.misc.ControllerEndpoints.FILM_CREW_MEMBERS_ENDPOINT;

@RestController
@RequestMapping(value = FILM_CREW_MEMBERS_ENDPOINT)
public class FilmCrewMemberController extends BaseControllerImpl<FilmCrewMember, Long, FilmCrewMemberDTO> {

    @Autowired
    public FilmCrewMemberController(FilmCrewMemberService filmCrewMemberService) {
        super(filmCrewMemberService);
    }

    @Override
    @PostMapping
    public ResponseEntity<Object> create(@Valid @RequestBody FilmCrewMemberDTO input) {
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
