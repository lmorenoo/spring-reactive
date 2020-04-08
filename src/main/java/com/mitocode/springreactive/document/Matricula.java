package com.mitocode.springreactive.document;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.List;

@Data
@NoArgsConstructor
@Document(collection = "matriculas")
public class Matricula {

  @Id
  private String id;

  private Long fechaMatricula = Instant.now().toEpochMilli();

  @NotNull
  @DBRef
  private Estudiante estudiante;

  @NotNull
  @DBRef
  private List<Curso> cursos;

  @NotNull
  private boolean estado;

  public Matricula(@NotNull Estudiante estudiante,
                   @NotNull List<Curso> cursos) {
    this.estudiante = estudiante;
    this.cursos = cursos;
    this.estado = Boolean.TRUE;
  }
}
