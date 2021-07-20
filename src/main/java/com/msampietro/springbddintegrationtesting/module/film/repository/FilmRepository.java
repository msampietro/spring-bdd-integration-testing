package com.msampietro.springbddintegrationtesting.module.film.repository;

import com.msampietro.springbddintegrationtesting.module.base.repository.ExtendedJpaRepository;
import com.msampietro.springbddintegrationtesting.module.film.model.Film;
import org.springframework.stereotype.Repository;

@Repository
public interface FilmRepository extends ExtendedJpaRepository<Film, Long> {
}
