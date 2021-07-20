package com.msampietro.springbddintegrationtesting.module.film.controller;

import com.msampietro.springbddintegrationtesting.exception.ObjectNotFoundException;
import com.msampietro.springbddintegrationtesting.module.base.controller.BaseControllerImpl;
import com.msampietro.springbddintegrationtesting.module.base.hal.PageModelWrapper;
import com.msampietro.springbddintegrationtesting.module.film.dto.FilmCrewMemberDTO;
import com.msampietro.springbddintegrationtesting.module.film.dto.FilmProductionCompanyDTO;
import com.msampietro.springbddintegrationtesting.module.film.hal.FilmCrewMemberResource;
import com.msampietro.springbddintegrationtesting.module.film.model.FilmProductionCompany;
import com.msampietro.springbddintegrationtesting.module.film.service.FilmCrewMemberService;
import com.msampietro.springbddintegrationtesting.module.film.service.FilmProductionCompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.msampietro.springbddintegrationtesting.misc.ControllerEndpoints.FILM_PRODUCTION_COMPANIES_ENDPOINT;

@RestController
@RequestMapping(value = FILM_PRODUCTION_COMPANIES_ENDPOINT)
public class FilmProductionCompanyController extends BaseControllerImpl<FilmProductionCompany, Long, FilmProductionCompanyDTO> {

    private final FilmProductionCompanyService filmProductionCompanyService;
    private final FilmCrewMemberService filmCrewMemberService;

    @Autowired
    public FilmProductionCompanyController(FilmProductionCompanyService filmProductionCompanyService,
                                           FilmCrewMemberService filmCrewMemberService) {
        super(filmProductionCompanyService);
        this.filmProductionCompanyService = filmProductionCompanyService;
        this.filmCrewMemberService = filmCrewMemberService;
    }

    @PutMapping(value = "{objectId}/film-crew-members")
    public ResponseEntity<FilmCrewMemberResource> addCrewMemberToCompany(@PathVariable Long objectId, @Valid @RequestBody FilmCrewMemberDTO input) throws ObjectNotFoundException {
        return ResponseEntity.ok(filmProductionCompanyService.addCrewMemberToCompany(objectId, input));
    }

    @DeleteMapping(value = "{objectId}/film-crew-members/{crewMemberId}")
    public ResponseEntity<FilmCrewMemberResource> removeCrewMemberFromCompany(@PathVariable Long objectId, @PathVariable Long crewMemberId) throws ObjectNotFoundException {
        return ResponseEntity.ok(filmProductionCompanyService.removeCrewMemberFromCompany(objectId, crewMemberId));
    }

    @GetMapping(value = "{objectId}/film-crew-members", params = {"page", "size"}, produces = {"application/hal+json"})
    public ResponseEntity<PageModelWrapper<FilmCrewMemberResource>> getAllCrewMembers(@PathVariable Long objectId,
                                                                                      @RequestParam int page,
                                                                                      @RequestParam int size) {
        var pageRequest = PageRequest.of(page - 1, size);
        var result = filmCrewMemberService.findAll(objectId, pageRequest);
        return ResponseEntity.ok(new PageModelWrapper<>(result, pageRequest));
    }

}
