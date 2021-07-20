package com.msampietro.springbddintegrationtesting.module.actor.service.impl;

import com.msampietro.springbddintegrationtesting.module.actor.model.Actor;
import com.msampietro.springbddintegrationtesting.module.actor.repository.ActorRepository;
import com.msampietro.springbddintegrationtesting.module.actor.service.ActorService;
import com.msampietro.springbddintegrationtesting.module.base.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActorServiceImpl extends BaseServiceImpl<Actor, Long> implements ActorService {

    @Autowired
    public ActorServiceImpl(ActorRepository actorRepository) {
        super(actorRepository);
    }

}

