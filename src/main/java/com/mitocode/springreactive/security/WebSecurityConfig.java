package com.mitocode.springreactive.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Mono;

//Numero7
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class WebSecurityConfig {

	private AuthenticationManager authenticationManager;

	private SecurityContextRepository securityContextRepository;

	@Autowired
	public WebSecurityConfig(AuthenticationManager authenticationManager, SecurityContextRepository securityContextRepository) {
		this.authenticationManager = authenticationManager;
		this.securityContextRepository = securityContextRepository;
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public SecurityWebFilterChain securitygWebFilterChain(ServerHttpSecurity http) {
		return http
				.exceptionHandling()
				.authenticationEntryPoint((swe, e) -> {					
					return Mono.fromRunnable(() -> {
						swe.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);						
					});
				}).accessDeniedHandler((swe, e) -> {					
					return Mono.fromRunnable(() -> {						
						swe.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
					});
				}).and()
				.csrf().disable()
				.formLogin().disable()
				.httpBasic().disable()
				.authenticationManager(authenticationManager)
				.securityContextRepository(securityContextRepository)
				.authorizeExchange()
				.pathMatchers(HttpMethod.OPTIONS).permitAll()				
				.pathMatchers("/login").permitAll()
				.pathMatchers("/v2/login").permitAll()
				.pathMatchers("/json/**").permitAll()
				.pathMatchers("/v2/api-docs",
						"/configuration/ui",
						"/swagger-resources/**",
						"/configuration/security",
						"/swagger-ui.html",
						"/webjars/**").permitAll()
				.anyExchange().authenticated()
				.and().build();
	}
	
}
