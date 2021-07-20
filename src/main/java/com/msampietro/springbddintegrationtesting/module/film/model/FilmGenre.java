package com.msampietro.springbddintegrationtesting.module.film.model;

import com.msampietro.springbddintegrationtesting.module.base.model.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "film_genre")
@Getter
@Setter
public class FilmGenre extends BaseEntity<Long> {

    @Column(nullable = false)
    private String name;

}
