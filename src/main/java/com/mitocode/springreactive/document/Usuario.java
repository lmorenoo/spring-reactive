package com.mitocode.springreactive.document;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@NoArgsConstructor
@Document(collection = "usuarios")
public class Usuario {

  @Id
  private String id;
  private String usuario;
  private String clave;
  private Boolean estado;
  @DBRef
  private List<Rol> roles;

}
