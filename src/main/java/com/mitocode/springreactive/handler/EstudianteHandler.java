package com.mitocode.springreactive.handler;

import com.mitocode.springreactive.document.Estudiante;
import com.mitocode.springreactive.service.IEstudianteService;
import com.mitocode.springreactive.validators.RequestValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;

import static org.springframework.web.reactive.function.BodyInserters.fromValue;

@Component
public class EstudianteHandler {

  private final IEstudianteService service;

  private final RequestValidator validadorGeneral;

  @Autowired
  public EstudianteHandler(IEstudianteService service,
                           RequestValidator validadorGeneral) {
    this.service = service;
    this.validadorGeneral = validadorGeneral;
  }

  public Mono<ServerResponse> listar(ServerRequest req) {
    return ServerResponse.ok()
        .contentType(MediaType.APPLICATION_STREAM_JSON)
        .body(service.listar(), Estudiante.class);
  }

  public Mono<ServerResponse> listarParalelamente(ServerRequest req) {
    return ServerResponse.ok()
        .contentType(MediaType.APPLICATION_STREAM_JSON)
        .body(service.listarParalelamente(), Estudiante.class);
  }

  public Mono<ServerResponse> listarPorId(ServerRequest req) {
    String dni = req.pathVariable("dni");

    return service.listarPorId(dni)
        .flatMap(p -> ServerResponse.ok()
            .contentType(MediaType.APPLICATION_STREAM_JSON)
            .body(fromValue(p))
        )
        .switchIfEmpty(ServerResponse
            .notFound()
            .build()
        );

  }

  public Mono<ServerResponse> registrar(ServerRequest req) {
    Mono<Estudiante> platoMono = req.bodyToMono(Estudiante.class);

    return platoMono
        .flatMap(this.validadorGeneral::validar)
        .flatMap(estudiante -> service.listarPorDni(estudiante.getDni())
            .flatMap(e -> ServerResponse.status(HttpStatus.BAD_REQUEST)
                .body(BodyInserters.fromValue("Estudiante ya existe")))
            .switchIfEmpty(service.registrar(estudiante)
                .flatMap(p -> ServerResponse.created(URI.create(req.uri().toString().concat("/").concat(p.getId())))
                    .contentType(MediaType.APPLICATION_STREAM_JSON)
                    .body(fromValue(p))))
        );
  }

  public Mono<ServerResponse> modificar(ServerRequest req) {
    Mono<Estudiante> platoMono = req.bodyToMono(Estudiante.class);

    return platoMono
        .flatMap(this.validadorGeneral::validar)
        .flatMap(estudiante -> service.listarPorId(estudiante.getId())
            .flatMap(estudianteEncontrado -> {
              estudiante.setId(estudianteEncontrado.getId());
              return service.modificar(estudiante)
                  .flatMap(p -> ServerResponse.ok()
                      .contentType(MediaType.APPLICATION_STREAM_JSON)
                      .body(fromValue(p)));
            }))
        .switchIfEmpty(ServerResponse.notFound().build());
  }

  public Mono<ServerResponse> eliminar(ServerRequest req) {
    String dni = req.pathVariable("dni");

    return service.listarPorDni(dni)
        .flatMap(p -> service.eliminar(p.getId())
            .then(ServerResponse
                .noContent()
                .build()
            )
        )
        .switchIfEmpty(ServerResponse
            .notFound()
            .build());
  }
}
