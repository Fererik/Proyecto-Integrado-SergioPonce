package com.tfg.Sergio.services;

import java.util.Date;
import java.util.List;

import com.tfg.Sergio.model.dto.CitaDTO;
import com.tfg.Sergio.model.dto.CitaDTOOut;


public interface CitaService {
    
    CitaDTOOut getCitaById(Long id);
    
    List<CitaDTOOut> getCitasByPaciente(Long pacienteId);
    
    
    List<CitaDTOOut> getCitasByMedico(Long medicoId);
    
    List<CitaDTOOut> getCitasByFechaBetween(Date fechaInicio, Date fechaFin);
    
    CitaDTOOut crearCita(CitaDTO citaDTOOut);
    
    CitaDTOOut actualizarCita(Long citaId, CitaDTO citaDTOOut);
    
    void cancelarCita(Long citaId);

	CitaDTOOut enlazarPaciente(Long pacienteId, Long citaId);

	

	
}
