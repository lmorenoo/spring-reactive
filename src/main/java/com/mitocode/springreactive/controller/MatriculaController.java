package com.mitocode.springreactive.controller;

import com.mitocode.springreactive.document.Estudiante;
import com.mitocode.springreactive.document.Matricula;
import com.mitocode.springreactive.dto.MatriculaDTO;
import com.mitocode.springreactive.exceptions.MatriculaException;
import com.mitocode.springreactive.service.ICursoService;
import com.mitocode.springreactive.service.IEstudianteService;
import com.mitocode.springreactive.service.IMatriculaService;
import com.mitocode.springreactive.utils.mapper.MatriculaMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/matriculas")
public class MatriculaController {

    private IMatriculaService matriculaService;

    private IEstudianteService estudianteService;

    private ICursoService cursoService;

    private MatriculaMapper matriculaMapper;

    @Autowired
    public MatriculaController(IMatriculaService matriculaService, IEstudianteService estudianteService,
                               ICursoService cursoService, MatriculaMapper matriculaMapper) {
        this.matriculaService = matriculaService;
        this.estudianteService = estudianteService;
        this.cursoService = cursoService;
        this.matriculaMapper = matriculaMapper;
    }

    @PostMapping
    public Mono<ResponseEntity<MatriculaDTO>> registrar(@Valid @RequestBody MatriculaDTO matricula,
                                                        final ServerHttpRequest req){

//        Mono<Matricula> matriculaMono = Mono.just(matricula).flatMap(m ->
//                matriculaService.listarPorEstudianteId(m.getEstudianteId())
//                        .switchIfEmpty(estudianteService.listarPorId(m.getEstudianteId())
//                                .zipWith(cursoService.listarPorIds(m.getCursos()).collectList())
//                                .flatMap(tuple ->
//                                        matriculaService.registrar(new Matricula(tuple.getT1(), tuple.getT2()))
//                                )
//                        )
//        );

        Mono<Matricula> matriculaMono = Mono.just(matricula).flatMap(m ->
                matriculaService.listarPorEstudianteId(m.getEstudianteId())
                        .switchIfEmpty(estudianteService.listarPorId(m.getEstudianteId())
                                .zipWith(cursoService.listarPorIds(m.getCursos()).collectList())
                                .flatMap(tuple ->
                                        matriculaService.registrar(new Matricula(tuple.getT1(), tuple.getT2()))
                                )
                        )
        );

        return matriculaMono
                .map(p -> ResponseEntity.created(URI.create(req.getURI().toString().concat("/")))
                        .contentType(MediaType.APPLICATION_STREAM_JSON)
                        .body(matriculaMapper.toDTO(p))
                ).onErrorMap(error -> new MatriculaException("Error al registar la matricula"));
    }

}
