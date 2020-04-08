package com.mitocode.springreactive.service;

import com.mitocode.springreactive.document.Curso;
import reactor.core.publisher.Flux;

import java.util.List;

public interface ICursoService extends ICRUD<Curso, String> {

  Flux<Curso> listarPorIds(List<String> ids);

}
