package com.msampietro.springbddintegrationtesting.module.actor.repository;

import com.msampietro.springbddintegrationtesting.module.actor.model.Actor;
import com.msampietro.springbddintegrationtesting.module.base.repository.ExtendedJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActorRepository extends ExtendedJpaRepository<Actor, Long> {
}
