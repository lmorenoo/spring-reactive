package com.mitocode.springreactive.document;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@Document(collection = "estudiantes")
public class Estudiante {

  @Id
  private String id;

  @NotEmpty
  private String nombres;

  @NotEmpty
  private String apellidos;

  @NotEmpty
  private String dni;

  @Min(4)
  @Max(100)
  private int edad;

}
