package com.tfg.Sergio.services.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tfg.Sergio.model.dto.CitaDTO;
import com.tfg.Sergio.model.dto.CitaDTOOut;
import com.tfg.Sergio.model.entitys.CitaEntity;
import com.tfg.Sergio.repository.CitaRepository;
import com.tfg.Sergio.repository.PacienteRepository;
import com.tfg.Sergio.services.CitaService;
import com.tfg.Sergio.services.converters.CitaConverter;

@Service
public class CitaServiceImpl implements CitaService {
	
	@Autowired
    private CitaRepository citaRepository;
	@Autowired
    private CitaConverter citaConverter;
	@Autowired
	private PacienteRepository pacienteRepository;
    
	 public List<CitaDTOOut> getCitas() {
	        
	        return citaConverter.toDtoList(this.citaRepository.findAll());
	    }

    @Override
    public CitaDTOOut getCitaById(Long id) {
        CitaEntity cita = citaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cita not found with id: " + id));
        return citaConverter.toDto(cita);
    }

    @Override
    public List<CitaDTOOut> getCitasByPaciente(Long pacienteId) {
        List<CitaEntity> citas = citaRepository.findByPacienteId(pacienteId).orElseThrow(() -> new RuntimeException("Cita not found with paciente_id: " + pacienteId));
        return citaConverter.toDtoList(citas);
    }

    @Override
    public List<CitaDTOOut> getCitasByMedico(Long medicoId) {
        List<CitaEntity> citas = citaRepository.findByMedicoId(medicoId).orElseThrow(() -> new RuntimeException("Cita not found with medicoId: " + medicoId));
        return citaConverter.toDtoList(citas);
    }

    @Override
    public List<CitaDTOOut> getCitasByFechaBetween(Date fechaInicio, Date fechaFin) {
        List<CitaEntity> citas = citaRepository.findByFechaBetween(fechaInicio, fechaFin);
        return citaConverter.toDtoList(citas);
    }

    @Override
    public CitaDTOOut crearCita(CitaDTO citaDTO) {
   
        return  this.citaConverter.toDto(citaRepository.save(this.citaConverter.toEntity(citaDTO)));
    }

    @Override
    public CitaDTOOut actualizarCita(Long citaId, CitaDTO citaDTO) {
    	CitaEntity aux = this.citaRepository.findById(citaId)
    			 .orElseThrow(() -> new RuntimeException("Cita not found with id: " + citaId));
    	aux.setEspecialidad(citaDTO.getEspecialidad());
    	aux.setFecha(citaDTO.getFecha());
    	
    	
        return this.citaConverter.toDto(this.citaRepository.save(aux));
    }

    @Override
    public void cancelarCita(Long citaId) {
       this.citaRepository.deleteById(citaId);
    }

	@Override
	public CitaDTOOut enlazarPaciente(Long pacienteId , Long citaId) {
		CitaEntity entidad=	this.citaRepository.findById(citaId).orElseThrow(() -> new RuntimeException("Cita not found with paciente_id: " + pacienteId));
			entidad.setPaciente(this.pacienteRepository.findById(pacienteId).orElseThrow(() -> new RuntimeException("Paciente not found with paciente_id: " + pacienteId)));
			
			
			return this.citaConverter.toDto(this.citaRepository.save(entidad));
	}
}


