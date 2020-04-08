package com.mitocode.springreactive.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@AllArgsConstructor
@Data
public class MatriculaDTO {

  @NotNull
  @NotEmpty
  String estudianteId;

  @NotNull
  List<String> cursos;
}
