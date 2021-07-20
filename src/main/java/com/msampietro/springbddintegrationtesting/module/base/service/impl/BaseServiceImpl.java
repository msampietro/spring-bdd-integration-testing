package com.msampietro.springbddintegrationtesting.module.base.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.msampietro.springbddintegrationtesting.exception.ObjectNotFoundException;
import com.msampietro.springbddintegrationtesting.exception.ObjectNotValidException;
import com.msampietro.springbddintegrationtesting.misc.Utils;
import com.msampietro.springbddintegrationtesting.module.base.dto.BaseDTO;
import com.msampietro.springbddintegrationtesting.module.base.model.BaseEntity;
import com.msampietro.springbddintegrationtesting.module.base.projection.BaseProjection;
import com.msampietro.springbddintegrationtesting.module.base.repository.ExtendedJpaRepository;
import com.msampietro.springbddintegrationtesting.module.base.service.BaseService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.GenericTypeResolver;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.projection.SpelAwareProxyProjectionFactory;
import org.springframework.hateoas.EntityModel;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

@Getter
public abstract class BaseServiceImpl<T extends BaseEntity<I>, I extends Serializable> implements BaseService<T, I> {

    @Autowired
    private ObjectMapper objectMapper;
    private final ExtendedJpaRepository<T, I> repository;
    private final Class<T> modelClazz;
    private final SpelAwareProxyProjectionFactory projectionFactory;
    @PersistenceContext
    private EntityManager entityManager;
    private static final String ENTITY_NOT_FOUND_TEMPLATE = "Entity with id %s does not exists";

    @SuppressWarnings("unchecked")
    protected BaseServiceImpl(ExtendedJpaRepository<T, I> repository) {
        this.repository = repository;
        Class<?>[] generics = GenericTypeResolver.resolveTypeArguments(getClass(), BaseServiceImpl.class);
        assert generics != null;
        this.modelClazz = (Class<T>) generics[0];
        this.projectionFactory = new SpelAwareProxyProjectionFactory();
    }

    @Override
    public I create(BaseDTO input) {
        T entity = getObjectMapper().convertValue(input, modelClazz);
        return getRepository().save(entity).getId();
    }

    @Transactional
    public <P extends BaseProjection<I>> EntityModel<P> patch(I id, JsonPatch input) throws ObjectNotFoundException, ObjectNotValidException {
        T entity = this.findEntityEagerlyById(id);
        T patchedEntity = repository.saveAndFlush(applyPatchToEntity(input, entity));
        this.getEntityManager().clear();
        T refreshedEntity = this.findEntityEagerlyById(patchedEntity.getId());
        Class<P> projectionClazz = Utils.getEntityProjectionClass(modelClazz);
        var projection = this.getProjectionFactory().createProjection(projectionClazz, refreshedEntity);
        return Utils.projectionToResourceMapping(modelClazz, projection);
    }

    protected T applyPatchToEntity(JsonPatch patch, T target) throws ObjectNotValidException {
        try {
            JsonNode patched = patch.apply(objectMapper.convertValue(target, JsonNode.class));
            return objectMapper.treeToValue(patched, modelClazz);
        } catch (JsonPatchException | JsonProcessingException e) {
            throw new ObjectNotValidException(String.format("An error occurred patching the entity with id %s", target.getId()));
        }
    }

    @Transactional
    public JsonNode delete(I id) throws ObjectNotFoundException {
        T entity = this.findEntityById(id);
        entity.setDeleted(true);
        entity.setDeletionToken(UUID.randomUUID());
        T result = repository.save(entity);
        ObjectNode response = this.getObjectMapper().createObjectNode();
        response.put("id", result.getId().toString());
        return response;
    }

    @Transactional(readOnly = true)
    @Override
    public <P extends BaseProjection<I>> EntityModel<P> findById(I id) throws ObjectNotFoundException {
        Class<P> projectionClazz = Utils.getEntityProjectionClass(modelClazz);
        var projection = repository.findProjectedById(id, projectionClazz)
                .orElseThrow(() -> new ObjectNotFoundException(String.format(ENTITY_NOT_FOUND_TEMPLATE, id.toString())));
        return Utils.projectionToResourceMapping(modelClazz, projection);
    }

    @Override
    public T findEntityById(I id) throws ObjectNotFoundException {
        return repository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(String.format(ENTITY_NOT_FOUND_TEMPLATE, id)));
    }

    protected T findEntityEagerlyById(I id) throws ObjectNotFoundException {
        var properties = new HashMap<String, Object>();
        properties.put("javax.persistence.fetchgraph", this.buildEagerlyFetchEntityGraph());
        Optional<T> result = Optional.ofNullable(entityManager.find(modelClazz, id, properties));
        return result
                .orElseThrow(() -> new ObjectNotFoundException(String.format(ENTITY_NOT_FOUND_TEMPLATE, id.toString())));
    }

    @Override
    public EntityGraph<T> buildEagerlyFetchEntityGraph() {
        return null;
    }

    @Transactional(readOnly = true)
    @Override
    public <P extends BaseProjection<I>> Page<EntityModel<P>> findAll(Pageable pageable) {
        Class<P> projectionClazz = Utils.getEntityProjectionClass(modelClazz);
        Page<P> pageResult = repository.findAllProjectedBy(projectionClazz, pageable);
        return pageResult.map(i -> Utils.projectionToResourceMapping(modelClazz, i));
    }

    public SpelAwareProxyProjectionFactory getProjectionFactory() {
        return this.projectionFactory;
    }

    public EntityManager getEntityManager() {
        return this.entityManager;
    }

}
