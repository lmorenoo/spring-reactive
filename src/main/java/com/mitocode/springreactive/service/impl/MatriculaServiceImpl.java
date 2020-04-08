package com.mitocode.springreactive.service.impl;

import com.mitocode.springreactive.document.Matricula;
import com.mitocode.springreactive.repo.IMatriculaRepo;
import com.mitocode.springreactive.service.IMatriculaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class MatriculaServiceImpl implements IMatriculaService {

  @Autowired
  private IMatriculaRepo repo;

  @Override
  public Mono<Matricula> registrar(Matricula t) {
    return repo.save(t);
  }

  @Override
  public Mono<Matricula> modificar(Matricula t) {
    return repo.save(t);
  }

  @Override
  public Flux<Matricula> listar() {
    return repo.findAll();
  }

  @Override
  public Mono<Matricula> listarPorId(String v) {
    return repo.findById(v);
  }

  @Override
  public Mono<Void> eliminar(String v) {
    return repo.deleteById(v);
  }

  @Override
  public Mono<Matricula> listarPorEstudianteId(String id) {
    return repo.findByEstudianteId(id);
  }
}
