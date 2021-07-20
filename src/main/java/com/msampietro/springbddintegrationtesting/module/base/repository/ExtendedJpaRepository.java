package com.msampietro.springbddintegrationtesting.module.base.repository;

import com.msampietro.springbddintegrationtesting.module.base.model.BaseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Optional;

@NoRepositoryBean
public interface ExtendedJpaRepository<T extends BaseEntity<I>, I extends Serializable> extends JpaRepository<T, I> {

    <P> Optional<P> findProjectedById(I id, Class<P> projectionClazz);

    <P> Page<P> findAllProjectedBy(Class<P> projectionClazz, Pageable pageable);

    @Transactional
    @Query("update #{#entityName} e set e.deleted = true, e.modifiedAt = current_timestamp, e.deletionToken = function('uuid_generate_v4') where e.id = :id")
    @Modifying
    void softDelete(@Param("id") I id);

}
