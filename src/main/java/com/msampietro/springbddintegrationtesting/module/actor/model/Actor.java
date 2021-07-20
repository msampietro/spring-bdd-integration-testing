package com.msampietro.springbddintegrationtesting.module.actor.model;

import com.msampietro.springbddintegrationtesting.module.base.model.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@Entity
@Table(name = "actor")
@SequenceGenerator(name = "base_seq_gen", sequenceName = "actor_seq", allocationSize = 1)
@Getter
@Setter
@Where(clause = "deleted is false")
public class Actor extends BaseEntity<Long> {

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String name;

}
