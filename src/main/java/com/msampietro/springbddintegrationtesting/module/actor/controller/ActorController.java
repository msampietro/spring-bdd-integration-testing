package com.msampietro.springbddintegrationtesting.module.actor.controller;

import com.msampietro.springbddintegrationtesting.module.actor.dto.ActorDTO;
import com.msampietro.springbddintegrationtesting.module.actor.model.Actor;
import com.msampietro.springbddintegrationtesting.module.actor.service.ActorService;
import com.msampietro.springbddintegrationtesting.module.base.controller.BaseControllerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/actors")
public class ActorController extends BaseControllerImpl<Actor, Long, ActorDTO> {

    @Autowired
    public ActorController(ActorService actorService) {
        super(actorService);
    }

}
