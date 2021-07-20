package com.msampietro.springbddintegrationtesting.module.film.service.impl;

import com.msampietro.springbddintegrationtesting.exception.ObjectNotFoundException;
import com.msampietro.springbddintegrationtesting.module.base.service.impl.BaseServiceImpl;
import com.msampietro.springbddintegrationtesting.module.film.dto.FilmCrewMemberDTO;
import com.msampietro.springbddintegrationtesting.module.film.hal.FilmCrewMemberResource;
import com.msampietro.springbddintegrationtesting.module.film.model.FilmCrewJob;
import com.msampietro.springbddintegrationtesting.module.film.model.FilmCrewMember;
import com.msampietro.springbddintegrationtesting.module.film.model.FilmProductionCompany;
import com.msampietro.springbddintegrationtesting.module.film.projection.FilmCrewMemberProjection;
import com.msampietro.springbddintegrationtesting.module.film.repository.FilmCrewMemberRepository;
import com.msampietro.springbddintegrationtesting.module.film.service.FilmCrewMemberService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityGraph;

import static com.msampietro.springbddintegrationtesting.functional.ThrowingFunction.throwingFunction;

@Service
public class FilmCrewMemberServiceImpl extends BaseServiceImpl<FilmCrewMember, Long> implements FilmCrewMemberService {

    private final FilmCrewMemberRepository filmCrewMemberRepository;

    public FilmCrewMemberServiceImpl(FilmCrewMemberRepository filmCrewMemberRepository) {
        super(filmCrewMemberRepository);
        this.filmCrewMemberRepository = filmCrewMemberRepository;
    }

    @Override
    public FilmCrewMemberResource addCrewMember(FilmProductionCompany filmProductionCompany, FilmCrewMemberDTO input) throws ObjectNotFoundException {
        var filmCrewMember = this.getObjectMapper().convertValue(input, FilmCrewMember.class);
        filmCrewMember.setFilmProductionCompany(filmProductionCompany);
        filmCrewMember.setFilmCrewJob(this.getEntityManager().getReference(FilmCrewJob.class, filmCrewMember.getFilmCrewJob().getId()));
        var result = filmCrewMemberRepository.saveAndFlush(filmCrewMember);
        this.getEntityManager().clear();
        var refreshedResult = this.findEntityEagerlyById(result.getId());
        var projection = this.getProjectionFactory().createProjection(FilmCrewMemberProjection.class, refreshedResult);
        return new FilmCrewMemberResource(projection);
    }

    @Override
    public FilmCrewMemberResource removeCrewMember(FilmProductionCompany filmProductionCompany, long filmCrewMemberId) throws ObjectNotFoundException {
        var filmCrewMember = this.findEntityEagerlyById(filmCrewMemberId);
        filmProductionCompany.getFilmCrewMembers().remove(filmCrewMember);
        var projection = this.getProjectionFactory().createProjection(FilmCrewMemberProjection.class, filmCrewMember);
        return new FilmCrewMemberResource(projection);
    }

    @Override
    public Page<FilmCrewMemberResource> findAll(long filmProductionCompanyId, Pageable pageable) {
        var pageResult = filmCrewMemberRepository
                .findAllProjectedByFilmProductionCompanyId(filmProductionCompanyId, pageable);
        return pageResult.map(throwingFunction(FilmCrewMemberResource::new));
    }

    @Override
    public EntityGraph<FilmCrewMember> buildEagerlyFetchEntityGraph() {
        var entityGraph = this.getEntityManager().createEntityGraph(FilmCrewMember.class);
        entityGraph.addSubgraph("filmCrewJob");
        return entityGraph;
    }
}
