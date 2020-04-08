package com.mitocode.springreactive.service.impl;

import com.mitocode.springreactive.document.Curso;
import com.mitocode.springreactive.repo.ICursoRepo;
import com.mitocode.springreactive.service.ICursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CursoServiceImpl implements ICursoService {

  private final ICursoRepo repo;

  @Autowired
  public CursoServiceImpl(ICursoRepo repo) {
    this.repo = repo;
  }

  @Override
  public Mono<Curso> registrar(Curso t) {
    return repo.save(t);
  }

  @Override
  public Mono<Curso> modificar(Curso t) {
    return repo.save(t);
  }

  @Override
  public Flux<Curso> listar() {
    return repo.findAll();
  }

  @Override
  public Mono<Curso> listarPorId(String v) {
    return repo.findById(v);
  }

  @Override
  public Mono<Void> eliminar(String v) {
    return repo.deleteById(v);
  }

  @Override
  public Flux<Curso> listarPorIds(List<String> ids) {
    return repo.findAll().filter(curso -> ids.contains(curso.getId()));
  }

}
