package com.msampietro.springbddintegrationtesting.module.base.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jsonpatch.JsonPatch;
import com.msampietro.springbddintegrationtesting.exception.ObjectNotFoundException;
import com.msampietro.springbddintegrationtesting.exception.ObjectNotValidException;
import com.msampietro.springbddintegrationtesting.module.base.dto.BaseDTO;
import com.msampietro.springbddintegrationtesting.module.base.model.BaseEntity;
import com.msampietro.springbddintegrationtesting.module.base.projection.BaseProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;

import javax.persistence.EntityGraph;
import java.io.Serializable;

public interface BaseService<T extends BaseEntity<I>, I extends Serializable> {

    I create(BaseDTO input);

    <P extends BaseProjection<I>> EntityModel<P> patch(I id, JsonPatch input) throws ObjectNotFoundException, ObjectNotValidException;

    JsonNode delete(I id) throws ObjectNotFoundException;

    <P extends BaseProjection<I>> EntityModel<P> findById(I id) throws ObjectNotFoundException;

    <P extends BaseProjection<I>> Page<EntityModel<P>> findAll(Pageable pageable);

    T findEntityById(I id) throws ObjectNotFoundException;

    EntityGraph<T> buildEagerlyFetchEntityGraph();

}
