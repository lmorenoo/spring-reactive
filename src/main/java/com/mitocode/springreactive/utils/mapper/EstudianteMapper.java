package com.mitocode.springreactive.utils.mapper;

import com.mitocode.springreactive.document.Estudiante;
import com.mitocode.springreactive.dto.EstudianteDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class EstudianteMapper {

    public Flux<EstudianteDTO> estudianteToDTO(Flux<Estudiante> estudiante) {
        return estudiante.map(this::toDTO);
    }

    public Flux<Estudiante> estudianteToEntity(Flux<EstudianteDTO> estudianteDTO) {
        return estudianteDTO.map(this::toEntity);
    }

    public Mono<EstudianteDTO> estudianteToDTO(Mono<Estudiante> estudiante) {
        return estudiante.map(this::toDTO);
    }

    public Mono<Estudiante> estudianteToEntity(Mono<EstudianteDTO> estudianteDTO) {
        return estudianteDTO.map(this::toEntity);
    }

    public EstudianteDTO toDTO(Estudiante estudiante) {
        EstudianteDTO estudianteDTO = new EstudianteDTO();
        BeanUtils.copyProperties(estudiante, estudianteDTO);
        return estudianteDTO;
    }

    public Estudiante toEntity(EstudianteDTO estudianteDTO) {
        Estudiante studentEntity = new Estudiante();
        BeanUtils.copyProperties(estudianteDTO, studentEntity);
        return studentEntity;
    }

}
