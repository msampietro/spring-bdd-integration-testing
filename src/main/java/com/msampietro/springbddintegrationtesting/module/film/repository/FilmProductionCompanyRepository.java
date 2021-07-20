package com.msampietro.springbddintegrationtesting.module.film.repository;

import com.msampietro.springbddintegrationtesting.module.base.repository.ExtendedJpaRepository;
import com.msampietro.springbddintegrationtesting.module.film.model.FilmProductionCompany;
import org.springframework.stereotype.Repository;

@Repository
public interface FilmProductionCompanyRepository extends ExtendedJpaRepository<FilmProductionCompany, Long> {
}
