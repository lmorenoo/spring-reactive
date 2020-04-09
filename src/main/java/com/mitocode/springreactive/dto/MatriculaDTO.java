package com.mitocode.springreactive.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MatriculaDTO {

  @NotNull(message = "El campo Id de estudiante es requerido")
  @NotEmpty (message = "El campo Id de estudiante es requerido")
  String estudianteId;

  @NotNull(message = "El campo cursos es requerido")
  List<String> cursos;
}
