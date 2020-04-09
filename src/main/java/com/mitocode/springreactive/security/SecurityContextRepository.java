package com.mitocode.springreactive.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class SecurityContextRepository implements ServerSecurityContextRepository{

	private AuthenticationManager authenticationManager;

	@Autowired
	public SecurityContextRepository(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

	@Override
	public Mono<Void> save(ServerWebExchange exchange, SecurityContext context) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public Mono<SecurityContext> load(ServerWebExchange swe) {
		ServerHttpRequest request = swe.getRequest();
		String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

		if (authHeader != null) {
			if (authHeader.startsWith("Bearer ") || authHeader.startsWith("bearer ") || authHeader.startsWith("BEARER ")) {
				String authToken = authHeader.substring(7); //extrae solo el token de bearear eyy...
				Authentication auth = new UsernamePasswordAuthenticationToken(authToken, authToken);
				return this.authenticationManager.authenticate(auth).map((authentication) -> {
					return new SecurityContextImpl(authentication);
				});
			}else {
				return Mono.error(new InterruptedException("No estas autorizado"));
			}
		}
		return Mono.empty();
	}

}
