package com.msampietro.springbddintegrationtesting.module.film.service;

import com.msampietro.springbddintegrationtesting.exception.ObjectNotFoundException;
import com.msampietro.springbddintegrationtesting.module.base.service.BaseService;
import com.msampietro.springbddintegrationtesting.module.film.dto.FilmCrewMemberDTO;
import com.msampietro.springbddintegrationtesting.module.film.hal.FilmCrewMemberResource;
import com.msampietro.springbddintegrationtesting.module.film.model.FilmProductionCompany;

public interface FilmProductionCompanyService extends BaseService<FilmProductionCompany, Long> {

    FilmCrewMemberResource addCrewMemberToCompany(long filmProductionCompanyId, FilmCrewMemberDTO input) throws ObjectNotFoundException;

    FilmCrewMemberResource removeCrewMemberFromCompany(long filmProductionCompanyId, long filmCrewMemberId) throws ObjectNotFoundException;

}
