package com.mitocode.springreactive.controller;

import com.mitocode.springreactive.dto.EstudianteDTO;
import com.mitocode.springreactive.exceptions.EstudianteException;
import com.mitocode.springreactive.service.IEstudianteService;
import com.mitocode.springreactive.utils.mapper.EstudianteMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import javax.validation.Valid;
import java.net.URI;
import java.util.Comparator;

@RestController
@RequestMapping("/api/estudiantes")
public class EstudianteController {

    private IEstudianteService estudianteService;

    private EstudianteMapper estudianteMapper;

    @Autowired
    public EstudianteController(IEstudianteService estudianteService, EstudianteMapper estudianteMapper) {
        this.estudianteService = estudianteService;
        this.estudianteMapper = estudianteMapper;
    }

    @GetMapping
    public Mono<ResponseEntity<Flux<EstudianteDTO>>> listar(){
        Flux<EstudianteDTO> fx = estudianteMapper.estudianteToDTO(estudianteService.listar())
                .sort(Comparator.comparing(EstudianteDTO::getEdad).reversed());

        return Mono.just(ResponseEntity.ok().contentType(MediaType.APPLICATION_STREAM_JSON).body(fx));
    }

    @GetMapping("/{idEstudiante}")
    public Mono<ResponseEntity<EstudianteDTO>> listarPorId(@Valid @PathVariable String idEstudiante){
        return estudianteService.listarPorId(idEstudiante)
                .map(p -> ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_STREAM_JSON)
                        .body(estudianteMapper.toDTO(p))
                )
                .defaultIfEmpty(ResponseEntity.notFound().build())
                .onErrorMap(error ->
                        new EstudianteException(String.format("Error al listar el estudiante con Id: %s",
                                idEstudiante))
                );
    }

    @PostMapping
    public Mono<ResponseEntity<EstudianteDTO>> registrar(@Valid @RequestBody EstudianteDTO estudianteDTO,
                                                        final ServerHttpRequest req){
        return estudianteService.registrar(estudianteMapper.toEntity(estudianteDTO))
                .map(p -> ResponseEntity.created(URI.create(req.getURI().toString().concat("/").concat(p.getId())))
                        .contentType(MediaType.APPLICATION_STREAM_JSON)
                        .body(estudianteMapper.toDTO(p))
                ).onErrorMap(error ->
                        new EstudianteException(String.format("Error al registrar el estudiante con Id: %s",
                                estudianteDTO.getId()))
                );
    }

    @PutMapping
    public Mono<ResponseEntity<EstudianteDTO>> modificar(@Valid @RequestBody EstudianteDTO estudianteDTO){
        return estudianteService.modificar(estudianteMapper.toEntity(estudianteDTO))
                .map(p -> ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_STREAM_JSON)
                        .body(estudianteMapper.toDTO(p))
                )
                .defaultIfEmpty(ResponseEntity.notFound().build())
                .onErrorMap(error ->
                        new EstudianteException(String.format("Error al registrar el estudiante con Id: %s",
                                estudianteDTO.getId()))
                );
    }

    @DeleteMapping("/{idEstudiante}")
    public Mono<ResponseEntity<Void>> eliminar(@Valid @PathVariable String idEstudiante){
        return estudianteService.listarPorId(idEstudiante)
                .flatMap(p ->
                    estudianteService.eliminar(p.getId())
                            .then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT)))
                )
                .defaultIfEmpty(ResponseEntity.notFound().build())
                .onErrorMap(error ->
                        new EstudianteException(String.format("Error al eliminar el estudiante con Id: %s", idEstudiante))
                );
    }

    @GetMapping("/sortedByAgeParallel")
    public Mono<ResponseEntity<Flux<EstudianteDTO>>> listarParalelo(){
        Flux<EstudianteDTO> fx = estudianteMapper.estudianteToDTO(estudianteService.listar())
                .parallel()
                .runOn(Schedulers.elastic())
                .ordered(Comparator.comparing(EstudianteDTO::getEdad).reversed());

        return Mono.just(ResponseEntity.ok().contentType(MediaType.APPLICATION_STREAM_JSON).body(fx));
    }

}
