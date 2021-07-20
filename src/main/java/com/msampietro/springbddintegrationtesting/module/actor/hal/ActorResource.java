package com.msampietro.springbddintegrationtesting.module.actor.hal;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.msampietro.springbddintegrationtesting.module.actor.controller.ActorController;
import com.msampietro.springbddintegrationtesting.module.actor.projection.ActorProjection;
import com.msampietro.springbddintegrationtesting.exception.ObjectNotFoundException;
import org.springframework.hateoas.EntityModel;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class ActorResource extends EntityModel<ActorProjection> {

    private final ActorProjection actorProjection;

    public ActorResource(ActorProjection actorProjection) throws ObjectNotFoundException {
        this.actorProjection = actorProjection;
        add(linkTo(methodOn(ActorController.class).get(actorProjection.getId())).withSelfRel());
    }

    @JsonUnwrapped
    public ActorProjection getActorProjection() {
        return this.actorProjection;
    }

}
