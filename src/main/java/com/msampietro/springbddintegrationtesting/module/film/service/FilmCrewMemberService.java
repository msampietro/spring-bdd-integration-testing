package com.msampietro.springbddintegrationtesting.module.film.service;

import com.msampietro.springbddintegrationtesting.exception.ObjectNotFoundException;
import com.msampietro.springbddintegrationtesting.module.base.service.BaseService;
import com.msampietro.springbddintegrationtesting.module.film.dto.FilmCrewMemberDTO;
import com.msampietro.springbddintegrationtesting.module.film.hal.FilmCrewMemberResource;
import com.msampietro.springbddintegrationtesting.module.film.model.FilmCrewMember;
import com.msampietro.springbddintegrationtesting.module.film.model.FilmProductionCompany;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FilmCrewMemberService extends BaseService<FilmCrewMember, Long> {

    FilmCrewMemberResource addCrewMember(FilmProductionCompany filmProductionCompany, FilmCrewMemberDTO input) throws ObjectNotFoundException;

    FilmCrewMemberResource removeCrewMember(FilmProductionCompany filmProductionCompany, long filmCrewMemberId) throws ObjectNotFoundException;

    Page<FilmCrewMemberResource> findAll(long filmProductionCompanyId, Pageable pageable);

}
