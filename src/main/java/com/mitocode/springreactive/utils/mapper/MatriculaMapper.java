package com.mitocode.springreactive.utils.mapper;

import com.mitocode.springreactive.document.Matricula;
import com.mitocode.springreactive.dto.MatriculaDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class MatriculaMapper {

    public MatriculaDTO toDTO(Matricula matricula) {
        MatriculaDTO matriculaDTO = new MatriculaDTO();
        BeanUtils.copyProperties(matricula, matriculaDTO);
        return matriculaDTO;
    }

}
