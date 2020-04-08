package com.mitocode.springreactive.service.impl;

import com.mitocode.springreactive.document.Estudiante;
import com.mitocode.springreactive.repo.IEstudianteRepo;
import com.mitocode.springreactive.service.IEstudianteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Comparator;

@Service
public class EstudianteServiceImpl implements IEstudianteService {

  private IEstudianteRepo repo;

  @Autowired
  public EstudianteServiceImpl(IEstudianteRepo repo) {
    this.repo = repo;
  }

  @Override
  public Mono<Estudiante> registrar(Estudiante t) {
    return repo.save(t);
  }

  @Override
  public Mono<Estudiante> modificar(Estudiante t) {
    return repo.save(t);
  }

  @Override
  public Flux<Estudiante> listar() {
    return repo.findAll().sort(Comparator.comparing(Estudiante::getEdad).reversed());
  }

  public Mono<Estudiante> listarPorId(String v) {
    if (StringUtils.isEmpty(v)) {
      return Mono.empty();
    }
    return repo.findById(v);
  }

  @Override
  public Mono<Void> eliminar(String v) {
    return repo.deleteById(v);
  }

  @Override
  public Mono<Estudiante> listarPorDni(String dni) {
    return repo.findByDni(dni);
  }

  @Override
  public Flux<Estudiante> listarParalelamente() {
    return repo.findAll().parallel().sorted(Comparator.comparing(Estudiante::getEdad).reversed());
  }
}
