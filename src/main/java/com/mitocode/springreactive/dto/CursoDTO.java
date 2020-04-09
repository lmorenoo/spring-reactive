package com.mitocode.springreactive.dto;

import lombok.Data;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class CursoDTO {

    @NotEmpty(message = "El campo Id es requerido")
    private String id;

    @NotEmpty(message = "El campo nombre es requerido")
    private String nombre;

    @NotEmpty(message = "El campo siglas es requerido")
    private String siglas;

    @NotNull(message = "El campo estado es requerido")
    private boolean estado;
}
