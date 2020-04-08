package com.mitocode.springreactive.service;

import com.mitocode.springreactive.document.Estudiante;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IEstudianteService extends ICRUD<Estudiante, String> {

  Mono<Estudiante> listarPorDni(String dni);

  Flux<Estudiante> listarParalelamente();

}
