package com.msampietro.springbddintegrationtesting.module.actor.projection;

import com.msampietro.springbddintegrationtesting.module.base.projection.BaseProjection;

public interface ActorProjection extends BaseProjection<Long> {

    String getName();

    String getLastName();

}
