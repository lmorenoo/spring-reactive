package com.mitocode.springreactive.controller;

import java.util.Date;

import com.mitocode.springreactive.security.AuthRequest;
import com.mitocode.springreactive.security.AuthResponse;
import com.mitocode.springreactive.security.ErrorLogin;
import com.mitocode.springreactive.security.JWTUtil;
import com.mitocode.springreactive.service.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

@RestController
public class LoginController {

	private JWTUtil jwtUtil;

	private IUsuarioService service;

	@Autowired
	public LoginController(JWTUtil jwtUtil, IUsuarioService service) {
		this.jwtUtil = jwtUtil;
		this.service = service;
	}

	@PostMapping("/login")
	public Mono<ResponseEntity<?>> login(@RequestBody AuthRequest ar) {
		return service.buscarPorUsuario(ar.getUsername()).map((userDetails) -> {
			String token = jwtUtil.generateToken(userDetails);
			Date expiracion = jwtUtil.getExpirationDateFromToken(token);
			
			if (BCrypt.checkpw(ar.getPassword(), userDetails.getPassword())) {
				return ResponseEntity.ok(new AuthResponse(token, expiracion));
			} else {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorLogin("credenciales incorrectas", new Date()));
			}
		}).defaultIfEmpty(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
	}
	
	@PostMapping("/v2/login")
	public Mono<ResponseEntity<?>> login(@RequestHeader("usuario") String usuario, @RequestHeader("clave") String clave) {
		
		return service.buscarPorUsuario(usuario).map((userDetails) -> {
			
			String token = jwtUtil.generateToken(userDetails);
			Date expiracion = jwtUtil.getExpirationDateFromToken(token);

			if (BCrypt.checkpw(clave, userDetails.getPassword())) {
				return ResponseEntity.ok(new AuthResponse(token, expiracion));
			} else {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorLogin("credenciales incorrectas", new Date()));
			}
		}).defaultIfEmpty(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
	}
}
