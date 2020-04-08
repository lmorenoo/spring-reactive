package com.mitocode.springreactive.repo;

import com.mitocode.springreactive.document.Matricula;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface IMatriculaRepo extends ReactiveMongoRepository<Matricula, String> {

  Mono<Matricula> findByEstudianteId(String id);
}
