package com.mitocode.springreactive.repo;

import com.mitocode.springreactive.document.Curso;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ICursoRepo extends ReactiveMongoRepository<Curso, String> {

}
