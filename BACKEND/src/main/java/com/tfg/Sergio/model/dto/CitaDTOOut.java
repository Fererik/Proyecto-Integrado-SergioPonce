package com.tfg.Sergio.model.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class CitaDTOOut {
    private Long id;
    private Date fecha;
    private String especialidad;
    private Long paciente_id;
    private Long medico_id;

    
}