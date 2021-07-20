package com.msampietro.springbddintegrationtesting.module.review.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.msampietro.springbddintegrationtesting.module.film.model.Film;
import com.msampietro.springbddintegrationtesting.module.base.model.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@Table(name = "review")
@SequenceGenerator(name = "base_seq_gen", sequenceName = "review_seq", allocationSize = 1)
@Getter
@Setter
@Where(clause = "deleted is false")
public class Review extends BaseEntity<Long> {

    @Column(nullable = false)
    private String comment;

    @Column(nullable = false)
    private String username;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "film_id", nullable = false, updatable = false)
    private Film film;

}
