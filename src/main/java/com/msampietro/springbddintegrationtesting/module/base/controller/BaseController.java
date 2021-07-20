package com.msampietro.springbddintegrationtesting.module.base.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jsonpatch.JsonPatch;
import com.msampietro.springbddintegrationtesting.exception.ObjectNotFoundException;
import com.msampietro.springbddintegrationtesting.exception.ObjectNotValidException;
import com.msampietro.springbddintegrationtesting.module.base.dto.BaseDTO;
import com.msampietro.springbddintegrationtesting.module.base.hal.PageModelWrapper;
import com.msampietro.springbddintegrationtesting.module.base.projection.BaseProjection;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;

import java.io.Serializable;

public interface BaseController<I extends Serializable, D extends BaseDTO> {

    ResponseEntity<Object> create(D input);

    ResponseEntity<EntityModel<? extends BaseProjection<? extends Serializable>>> patch(I objectId, JsonPatch input) throws ObjectNotValidException, ObjectNotFoundException;

    ResponseEntity<JsonNode> delete(I objectId) throws ObjectNotFoundException;

    ResponseEntity<EntityModel<? extends BaseProjection<? extends Serializable>>> get(I objectId) throws ObjectNotFoundException;

    ResponseEntity<PageModelWrapper<EntityModel<BaseProjection<I>>>> getAll(int page, int size) throws ObjectNotValidException, ObjectNotFoundException;

}
