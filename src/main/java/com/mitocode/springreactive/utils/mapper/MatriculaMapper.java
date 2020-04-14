package com.mitocode.springreactive.utils.mapper;

import com.mitocode.springreactive.document.Matricula;
import com.mitocode.springreactive.dto.MatriculaDTO;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class MatriculaMapper {

    public MatriculaDTO toDTO(Matricula matricula) {
        MatriculaDTO matriculaDTO = new MatriculaDTO();
        matriculaDTO.setEstudianteId(matricula.getEstudiante().getId());
        matriculaDTO.setCursos(matricula.getCursos().stream().map(curso -> curso.getId()).collect(Collectors.toList()));
        return matriculaDTO;
    }

}
