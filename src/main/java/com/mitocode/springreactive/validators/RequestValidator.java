package com.mitocode.springreactive.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.util.Set;

@Component
public class RequestValidator {

	@Autowired
	private Validator validador;

	public <T> Mono<T> validar(T obj){
		
		if(obj == null) {
			return Mono.error(new IllegalArgumentException());
		}
		
		Set<ConstraintViolation<T>> violaciones = this.validador.validate(obj);
		
		if(violaciones == null || violaciones.isEmpty()) {
			return Mono.just(obj);
		}
		
		return Mono.error(new ConstraintViolationException(violaciones));
	}
}
