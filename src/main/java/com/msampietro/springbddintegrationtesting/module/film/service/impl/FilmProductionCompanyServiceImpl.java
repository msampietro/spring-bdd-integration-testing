package com.msampietro.springbddintegrationtesting.module.film.service.impl;

import com.msampietro.springbddintegrationtesting.exception.ObjectNotFoundException;
import com.msampietro.springbddintegrationtesting.module.base.service.impl.BaseServiceImpl;
import com.msampietro.springbddintegrationtesting.module.film.dto.FilmCrewMemberDTO;
import com.msampietro.springbddintegrationtesting.module.film.hal.FilmCrewMemberResource;
import com.msampietro.springbddintegrationtesting.module.film.model.FilmProductionCompany;
import com.msampietro.springbddintegrationtesting.module.film.repository.FilmProductionCompanyRepository;
import com.msampietro.springbddintegrationtesting.module.film.service.FilmCrewMemberService;
import com.msampietro.springbddintegrationtesting.module.film.service.FilmProductionCompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FilmProductionCompanyServiceImpl extends BaseServiceImpl<FilmProductionCompany, Long> implements FilmProductionCompanyService {

    private final FilmCrewMemberService filmCrewMemberService;

    @Autowired
    public FilmProductionCompanyServiceImpl(FilmProductionCompanyRepository filmProductionCompanyRepository,
                                            FilmCrewMemberService filmCrewMemberService) {
        super(filmProductionCompanyRepository);
        this.filmCrewMemberService = filmCrewMemberService;
    }

    @Transactional
    @Override
    public FilmCrewMemberResource addCrewMemberToCompany(long filmProductionCompanyId, FilmCrewMemberDTO input) throws ObjectNotFoundException {
        var foundFilmProductionCompany = this.findEntityById(filmProductionCompanyId);
        return filmCrewMemberService.addCrewMember(foundFilmProductionCompany, input);
    }

    @Transactional
    @Override
    public FilmCrewMemberResource removeCrewMemberFromCompany(long filmProductionCompanyId, long filmCrewMemberId) throws ObjectNotFoundException {
        var foundFilmProductionCompany = this.findEntityById(filmProductionCompanyId);
        return filmCrewMemberService.removeCrewMember(foundFilmProductionCompany, filmCrewMemberId);
    }

}
