package com.msampietro.springbddintegrationtesting.module.base.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jsonpatch.JsonPatch;
import com.msampietro.springbddintegrationtesting.exception.ObjectNotFoundException;
import com.msampietro.springbddintegrationtesting.exception.ObjectNotValidException;
import com.msampietro.springbddintegrationtesting.module.base.dto.BaseDTO;
import com.msampietro.springbddintegrationtesting.module.base.hal.PageModelWrapper;
import com.msampietro.springbddintegrationtesting.module.base.model.BaseEntity;
import com.msampietro.springbddintegrationtesting.module.base.projection.BaseProjection;
import com.msampietro.springbddintegrationtesting.module.base.service.BaseService;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.io.Serializable;

public abstract class BaseControllerImpl<T extends BaseEntity<I>, I extends Serializable, D extends BaseDTO> implements BaseController<I, D> {

    private final BaseService<T, I> baseService;

    protected BaseControllerImpl(BaseService<T, I> baseService) {
        this.baseService = baseService;
    }

    @Override
    @PostMapping
    public ResponseEntity<Object> create(@Valid @RequestBody D input) {
        var id = baseService.create(input);
        var location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(id).toUri();
        return ResponseEntity.created(location).build();
    }

    @Override
    @PatchMapping(path = "{objectId}", consumes = "application/json-patch+json")
    public ResponseEntity<EntityModel<? extends BaseProjection<? extends Serializable>>> patch(@PathVariable I objectId, @RequestBody JsonPatch input) throws ObjectNotValidException, ObjectNotFoundException {
        return ResponseEntity.ok(baseService.patch(objectId, input));
    }

    @Override
    @DeleteMapping(value = "{objectId}")
    public ResponseEntity<JsonNode> delete(@PathVariable I objectId) throws ObjectNotFoundException {
        return ResponseEntity.ok(baseService.delete(objectId));
    }

    @Override
    @GetMapping(value = "{objectId}", produces = {"application/hal+json"})
    public ResponseEntity<EntityModel<? extends BaseProjection<? extends Serializable>>> get(@PathVariable I objectId) throws ObjectNotFoundException {
        return ResponseEntity.ok(baseService.findById(objectId));
    }

    @Override
    @GetMapping(params = {"page", "size"}, produces = {"application/hal+json"})
    public ResponseEntity<PageModelWrapper<EntityModel<BaseProjection<I>>>> getAll(@RequestParam int page, @RequestParam int size) {
        var pageRequest = PageRequest.of(page - 1, size);
        var result = baseService.findAll(pageRequest);
        return ResponseEntity.ok(new PageModelWrapper<>(result, pageRequest));
    }

}
