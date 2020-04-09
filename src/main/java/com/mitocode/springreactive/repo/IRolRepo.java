package com.mitocode.springreactive.repo;

import com.mitocode.springreactive.document.Rol;
import com.mitocode.springreactive.document.Usuario;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface IRolRepo extends ReactiveMongoRepository<Rol, String> {

}
