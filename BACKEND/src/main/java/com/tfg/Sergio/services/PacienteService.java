package com.tfg.Sergio.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tfg.Sergio.model.dto.PacienteDTO;
import com.tfg.Sergio.model.dto.PacienteDTOOut;


public interface PacienteService {
    
    PacienteDTOOut crearPaciente(PacienteDTO pacienteDTO);
    
    List<PacienteDTOOut> obtenerPacientes();
    
    PacienteDTOOut obtenerPacientePorId(Long id);
    
    PacienteDTOOut actualizarPaciente(Long id, PacienteDTO pacienteDTO);
    
    void eliminarPaciente(Long id);
    
    //List<CitaDTO> obtenerCitasDePaciente(Long idPaciente);
    
   
}
