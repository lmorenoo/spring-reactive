package com.mitocode.springreactive.document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "cursos")
public class Curso {

  @NotEmpty
  @Id
  private String id;

  @NotEmpty
  private String nombre;

  @NotEmpty
  private String siglas;

  @NotNull
  private boolean estado;

}
