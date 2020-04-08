package com.mitocode.springreactive.routes;

import com.mitocode.springreactive.handler.CursoHandler;
import com.mitocode.springreactive.handler.EstudianteHandler;
import com.mitocode.springreactive.handler.MatriculaHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.PUT;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterConfig {

  @Bean
  public RouterFunction<ServerResponse> rutasCursos(CursoHandler handler) {
    return route(GET("/cursos"), handler::listar)
        .andRoute(GET("/cursos/{id}"), handler::listarPorId)
        .andRoute(POST("/cursos"), handler::registrar)
        .andRoute(PUT("/cursos"), handler::modificar)
        .andRoute(DELETE("/cursos/{id}"), handler::eliminar);
  }

  @Bean
  public RouterFunction<ServerResponse> rutasEstudiantes(EstudianteHandler handler) {
    return route(GET("/estudiantes"), handler::listar)
        .andRoute(GET("/estudiantesParallel"), handler::listarParalelamente)
        .andRoute(GET("/estudiantes/{dni}"), handler::listarPorId)
        .andRoute(POST("/estudiantes"), handler::registrar)
        .andRoute(PUT("/estudiantes"), handler::modificar)
        .andRoute(DELETE("/estudiantes/{dni}"), handler::eliminar);
  }

  @Bean
  public RouterFunction<ServerResponse> rutasMatriculas(MatriculaHandler handler) {
    return route(POST("/matriculas"), handler::registrar);
  }
}
