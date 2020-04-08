package com.mitocode.springreactive.repo;

import com.mitocode.springreactive.document.Estudiante;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface IEstudianteRepo extends ReactiveMongoRepository<Estudiante, String> {

  Mono<Estudiante> findByDni(String dni);

}
