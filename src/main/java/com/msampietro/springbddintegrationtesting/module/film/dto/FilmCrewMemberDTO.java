package com.msampietro.springbddintegrationtesting.module.film.dto;

import com.msampietro.springbddintegrationtesting.module.base.dto.BaseDTO;
import com.msampietro.springbddintegrationtesting.module.base.dto.IdDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class FilmCrewMemberDTO extends BaseDTO {

    @NotBlank
    @Size(max = 50)
    private String name;

    @NotBlank
    @Size(max = 50)
    private String lastName;

    @Valid
    @NotNull
    private IdDTO<Long> filmCrewJob;
}
