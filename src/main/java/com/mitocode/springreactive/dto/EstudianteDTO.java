package com.mitocode.springreactive.dto;

import lombok.Data;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.*;

@Data
public class EstudianteDTO {

    @NotEmpty(message = "El campo Id es requerido")
    private String id;

    @NotEmpty(message = "El campo nombres es requerido")
    private String nombres;

    @NotEmpty(message = "El campo apellidos es requerido")
    private String apellidos;

    @NotEmpty(message = "El DNI Id es requerido")
    private String dni;

    @NotNull(message = "El campo edad es requerido")
    @Positive(message = "El campo edad debe ser positivo")
    @Min(value = 4, message = "El campo edad debe ser minimo de 4 años")
    @Max(value = 99, message = "El campo edad debe ser maximo de 5 años")
    private int edad;
}
