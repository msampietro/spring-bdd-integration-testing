package com.msampietro.springbddintegrationtesting.module.film.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.msampietro.springbddintegrationtesting.module.base.model.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@Table(name = "film_crew_member")
@SequenceGenerator(name = "base_seq_gen", sequenceName = "film_crew_member_seq", allocationSize = 1)
@Getter
@Setter
@Where(clause = "deleted is false")
public class FilmCrewMember extends BaseEntity<Long> {

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String name;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "film_crew_job_id")
    private FilmCrewJob filmCrewJob;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "film_production_company_id", nullable = false, updatable = false)
    private FilmProductionCompany filmProductionCompany;

}
