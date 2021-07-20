package com.msampietro.springbddintegrationtesting.module.film.repository;

import com.msampietro.springbddintegrationtesting.module.base.repository.ExtendedJpaRepository;
import com.msampietro.springbddintegrationtesting.module.film.model.FilmCrewMember;
import com.msampietro.springbddintegrationtesting.module.film.projection.FilmCrewMemberProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface FilmCrewMemberRepository extends ExtendedJpaRepository<FilmCrewMember, Long> {

    Page<FilmCrewMemberProjection> findAllProjectedByFilmProductionCompanyId(long filmProductionCompanyId, Pageable pageable);

}
