package com.mitocode.springreactive.service;

import com.mitocode.springreactive.document.Matricula;
import reactor.core.publisher.Mono;

public interface IMatriculaService extends ICRUD<Matricula, String> {

  Mono<Matricula> listarPorEstudianteId(String dniEstudiante);
}
