package com.msampietro.springbddintegrationtesting.module.film.dto;

import com.msampietro.springbddintegrationtesting.module.base.dto.BaseDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@NoArgsConstructor
@Getter
@Setter
public class FilmProductionCompanyDTO extends BaseDTO {

    @NotBlank
    @Size(max = 50)
    private String name;

}
