package com.mitocode.springreactive.controller;

import com.mitocode.springreactive.dto.CursoDTO;
import com.mitocode.springreactive.exceptions.CursoException;
import com.mitocode.springreactive.service.ICursoService;
import com.mitocode.springreactive.utils.mapper.CursoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;


@RestController
@RequestMapping("/api/cursos")
public class CursoController {

    private ICursoService cursoService;

    private CursoMapper cursoMapper;

    @Autowired
    public CursoController(ICursoService cursoService, CursoMapper cursoMapper) {
        this.cursoService = cursoService;
        this.cursoMapper = cursoMapper;
    }

    @GetMapping()
    public Mono<ResponseEntity<Flux<CursoDTO>>> listar(){
        Flux<CursoDTO> courseFlux = cursoMapper.cursoToDTO(cursoService.listar());

        return Mono.just(ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_STREAM_JSON)
                .body(courseFlux));
    }

    @GetMapping("/{idCurso}")
    public Mono<ResponseEntity<CursoDTO>> listarPorId(@Valid @PathVariable String idCurso){
       return cursoService.listarPorId(idCurso)
                .map(p -> ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_STREAM_JSON)
                        .body(cursoMapper.toDTO(p))
                )
               .defaultIfEmpty(ResponseEntity.notFound().build())
               .onErrorMap(error ->
                       new CursoException(String.format("Error al listar el curso con Id: %s", idCurso))
               );
    }

    @GetMapping("/listarPorIds")
    public Mono<ResponseEntity<Flux<CursoDTO>>> listarPorIds(@RequestParam List<String> ids){
        Flux<CursoDTO> courseFlux =cursoMapper.cursoToDTO(cursoService.listarPorIds(ids));
        return Mono.just(ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_STREAM_JSON)
                .body(courseFlux));
    }


    @PostMapping
    public Mono<ResponseEntity<CursoDTO>> registrar(@Valid @RequestBody CursoDTO curso, final ServerHttpRequest req){
        return cursoService.registrar(cursoMapper.toEntity(curso))
                .map(p -> ResponseEntity.created(URI.create(req.getURI().toString().concat("/").concat(p.getId())))
                        .contentType(MediaType.APPLICATION_STREAM_JSON)
                        .body(cursoMapper.toDTO(p))
                ).onErrorMap(error ->
                        new CursoException(String.format("Error al registrar el curso con Id: %s", curso.getId()))
                );
    }

    @PutMapping
    public Mono<ResponseEntity<CursoDTO>> modificar(@Valid @RequestBody CursoDTO curso){
        return cursoService.modificar(cursoMapper.toEntity(curso))
                .map(p -> ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_STREAM_JSON)
                        .body(cursoMapper.toDTO(p))
                )
                .defaultIfEmpty(ResponseEntity.notFound().build())
                .onErrorMap(error ->
                        new CursoException(String.format("Error al modificar el curso con Id: %s", curso.getId()))
                );
    }

    @DeleteMapping("/{idCurso}")
    public Mono<ResponseEntity<Void>> eliminar(@Valid @PathVariable String idCurso){
        return cursoService.listarPorId(idCurso)
                .flatMap(p ->
                    cursoService.eliminar(p.getId())
                            .then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT)))
                )
                .defaultIfEmpty(ResponseEntity.notFound().build())
                .onErrorMap(error ->
                        new CursoException(String.format("Error al eliminar el curso con Id: %s", idCurso))
                );
    }
}
