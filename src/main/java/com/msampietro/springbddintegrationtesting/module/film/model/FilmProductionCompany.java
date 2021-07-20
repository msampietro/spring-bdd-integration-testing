package com.msampietro.springbddintegrationtesting.module.film.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.msampietro.springbddintegrationtesting.module.base.model.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "film_production_company")
@SequenceGenerator(name = "base_seq_gen", sequenceName = "film_production_company_seq", allocationSize = 1)
@Getter
@Setter
@Where(clause = "deleted is false")
public class FilmProductionCompany extends BaseEntity<Long> {

    @Column(nullable = false)
    private String name;

    @JsonManagedReference
    @OneToMany(mappedBy = "filmProductionCompany", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FilmCrewMember> filmCrewMembers = new ArrayList<>();

}
