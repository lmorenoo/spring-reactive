package com.mitocode.springreactive.utils.mapper;

import com.mitocode.springreactive.document.Curso;
import com.mitocode.springreactive.dto.CursoDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class CursoMapper {

    public Flux<CursoDTO> cursoToDTO(Flux<Curso> curso) {
        return curso.map(this::toDTO);
    }

    public Flux<Curso> cursoToEntity(Flux<CursoDTO> cursoDTO) {
        return cursoDTO.map(this::toEntity);
    }

    public Mono<CursoDTO> cursoToDTO(Mono<Curso> curso) {
        return curso.map(this::toDTO);
    }

    public Mono<Curso> cursoToEntity(Mono<CursoDTO> cursoDTO) {
        return cursoDTO.map(this::toEntity);
    }

    public CursoDTO toDTO(Curso curso) {
        CursoDTO cursoDTO = new CursoDTO();
        BeanUtils.copyProperties(curso, cursoDTO);
        return cursoDTO;
    }

    public Curso toEntity(CursoDTO cursoDTO) {
        Curso courseEntity = new Curso();
        BeanUtils.copyProperties(cursoDTO, courseEntity);
        return courseEntity;
    }
}
