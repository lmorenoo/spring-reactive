package com.mitocode.springreactive.service;

import com.mitocode.springreactive.document.Usuario;
import com.mitocode.springreactive.security.User;

import reactor.core.publisher.Mono;

public interface IUsuarioService extends ICRUD<Usuario, String> {

  Mono<User> buscarPorUsuario(String usuario);

}
