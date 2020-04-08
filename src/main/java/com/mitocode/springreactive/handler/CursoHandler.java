package com.mitocode.springreactive.handler;

import com.mitocode.springreactive.document.Curso;
import com.mitocode.springreactive.service.ICursoService;
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
public class CursoHandler {

  private ICursoService service;

  private RequestValidator validadorGeneral;

  @Autowired
  public CursoHandler(ICursoService service, RequestValidator validadorGeneral) {
    this.service = service;
    this.validadorGeneral = validadorGeneral;
  }

  public Mono<ServerResponse> listar(ServerRequest req) {
    return ServerResponse.ok()
        .contentType(MediaType.APPLICATION_STREAM_JSON)
        .body(service.listar(), Curso.class);
  }

  public Mono<ServerResponse> listarPorId(ServerRequest req) {
    String id = req.pathVariable("id");

    return service.listarPorId(id)
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
    Mono<Curso> platoMono = req.bodyToMono(Curso.class);

    return platoMono
        .flatMap(this.validadorGeneral::validar)
        .flatMap(curso -> service.listarPorId(curso.getId())
            .flatMap(c -> ServerResponse.status(HttpStatus.BAD_REQUEST)
                .body(BodyInserters.fromValue("Curso ya existe")))
            .switchIfEmpty(service.registrar(curso)
                .flatMap(p -> ServerResponse.created(URI.create(req.uri().toString().concat("/").concat(p.getId())))
                    .contentType(MediaType.APPLICATION_STREAM_JSON)
                    .body(fromValue(p))))
        );
  }

  public Mono<ServerResponse> modificar(ServerRequest req) {
    Mono<Curso> platoMono = req.bodyToMono(Curso.class);

    return platoMono
        .flatMap(this.validadorGeneral::validar)
        .flatMap(curso -> service.listarPorId(curso.getId())
            .flatMap(cursoEncontrado -> {
              if (cursoEncontrado != null) {
                return service.registrar(curso)
                    .flatMap(p -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_STREAM_JSON)
                        .body(fromValue(p)));
              } else {
                return ServerResponse
                    .notFound()
                    .build();
              }
            }));

  }

  public Mono<ServerResponse> eliminar(ServerRequest req) {
    String id = req.pathVariable("id");

    return service.listarPorId(id)
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
