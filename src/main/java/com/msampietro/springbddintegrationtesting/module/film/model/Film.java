package com.msampietro.springbddintegrationtesting.module.film.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.msampietro.springbddintegrationtesting.module.actor.model.Actor;
import com.msampietro.springbddintegrationtesting.module.base.model.BaseEntity;
import com.msampietro.springbddintegrationtesting.module.review.model.Review;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "film")
@SequenceGenerator(name = "base_seq_gen", sequenceName = "film_seq", allocationSize = 1)
@Getter
@Setter
@Where(clause = "deleted is false")
public class Film extends BaseEntity<Long> {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer ranking;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "film_genre_id", nullable = false)
    private FilmGenre filmGenre;

    @OneToOne
    @JoinColumn(name = "film_production_company_id", nullable = false)
    private FilmProductionCompany filmProductionCompany;

    @ManyToMany(cascade = {CascadeType.REFRESH})
    @JoinTable(name = "film_actor",
            joinColumns = {@JoinColumn(name = "film_id")},
            inverseJoinColumns = {@JoinColumn(name = "actor_id")})
    private List<Actor> actors = new ArrayList<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "film", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews = new ArrayList<>();

}