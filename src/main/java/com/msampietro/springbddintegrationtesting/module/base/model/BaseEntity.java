package com.msampietro.springbddintegrationtesting.module.base.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@MappedSuperclass
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity<I extends Serializable> implements Serializable {

    private static final String UUID_NIL = "00000000-0000-0000-0000-000000000000";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "base_seq_gen")
    @Column(updatable = false, nullable = false)
    private I id;

    @Column(name = "deleted", columnDefinition = "boolean DEFAULT false", nullable = false)
    private boolean deleted = false;

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "modified_at", updatable = false)
    private LocalDateTime modifiedAt;

    @JsonIgnore
    @Column(name = "deletion_token", columnDefinition = "uuid DEFAULT uuid_nil()", nullable = false)
    private UUID deletionToken = UUID.fromString(UUID_NIL);

}
