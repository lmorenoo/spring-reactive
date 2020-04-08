package com.mitocode.springreactive.handler;

import com.mitocode.springreactive.document.Matricula;
import com.mitocode.springreactive.dto.MatriculaDTO;
import com.mitocode.springreactive.service.ICursoService;
import com.mitocode.springreactive.service.IEstudianteService;
import com.mitocode.springreactive.service.IMatriculaService;
import com.mitocode.springreactive.validators.RequestValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;

import static org.springframework.web.reactive.function.BodyInserters.fromValue;
import static org.springframework.web.reactive.function.server.ServerResponse.created;

@Component
public class MatriculaHandler {

  private IMatriculaService service;

  private IEstudianteService estudianteService;

  private ICursoService cursoService;

  private RequestValidator validadorGeneral;

  @Autowired
  public MatriculaHandler(IMatriculaService service,
                          IEstudianteService estudianteService,
                          ICursoService cursoService,
                          RequestValidator validadorGeneral) {
    this.service = service;
    this.estudianteService = estudianteService;
    this.cursoService = cursoService;
    this.validadorGeneral = validadorGeneral;
  }

  public Mono<ServerResponse> registrar(ServerRequest req) {
    Mono<MatriculaDTO> matriculaMono = req.bodyToMono(MatriculaDTO.class);

    return matriculaMono
        .flatMap(this.validadorGeneral::validar)
        .flatMap(m -> service.listarPorEstudianteId(m.getEstudianteId())
            .flatMap(matriculaExistente -> ServerResponse.status(HttpStatus.BAD_REQUEST)
                .body(fromValue("Matricula ya existe")))
            .switchIfEmpty(estudianteService.listarPorId(m.getEstudianteId())
                .zipWith(cursoService.listarPorIds(m.getCursos()).collectList())
                .flatMap(tuple -> service.registrar(new Matricula(tuple.getT1(), tuple.getT2()))
                    .flatMap(p -> created(URI.create(req.uri().toString().concat("/").concat(p.getId())))
                        .contentType(MediaType.APPLICATION_STREAM_JSON)
                        .body(fromValue(p))))
                .switchIfEmpty(ServerResponse.notFound().build())));
  }

}
