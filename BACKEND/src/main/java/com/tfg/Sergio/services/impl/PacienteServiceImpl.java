package com.tfg.Sergio.services.impl;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tfg.Sergio.model.dto.MedicoDTOOut;
import com.tfg.Sergio.model.dto.PacienteDTO;
import com.tfg.Sergio.model.dto.PacienteDTOOut;
import com.tfg.Sergio.model.entitys.PacienteEntity;
import com.tfg.Sergio.model.entitys.UsuarioEntity;
import com.tfg.Sergio.repository.PacienteRepository;
import com.tfg.Sergio.seguridad.Encriptadores;
import com.tfg.Sergio.services.PacienteService;
import com.tfg.Sergio.services.converters.PacienteConverter;

@Service
public class PacienteServiceImpl implements PacienteService {
	@Autowired
	private PacienteRepository pacienteRepository;
	@Autowired
	private PacienteConverter pacienteConverter;
	
	@Autowired
	Encriptadores passwordEncoder;

	@Override
	public PacienteDTOOut crearPaciente(PacienteDTO pacienteDTO) {
		   PacienteEntity paciente =  this.pacienteConverter.toEntity(pacienteDTO);
		   paciente.setPassword(this.passwordEncoder.encode(paciente.getPassword()));
	        paciente = pacienteRepository.save(paciente);
	        return this.pacienteConverter.toDto(paciente);
	    
	}

	@Override
	public List<PacienteDTOOut> obtenerPacientes() {
		List<PacienteEntity> entityList = this.pacienteRepository.findAll();
		return this.pacienteConverter.toDtoList(entityList);
	}

	@Override
	public PacienteDTOOut obtenerPacientePorId(Long id) {
		 PacienteEntity paciente = pacienteRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Paciente con id " + id + " no encontrado"));
	        
		return this.pacienteConverter.toDto(paciente);
	}

	@Override
	public PacienteDTOOut actualizarPaciente(Long id, PacienteDTO dto) {
		PacienteEntity entity = pacienteRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Paciente con id " + id + " no encontrado"));
		 entity.setNombre(dto.getNombre());
	        entity.setApellidos(dto.getApellidos());
	        entity.setEmail(dto.getEmail());
	        entity.setNumeroPoliza(dto.getNumPoliza());
	        pacienteRepository.save(entity);
	        
		return this.pacienteConverter.toDto(entity);
	}
	
public List<PacienteEntity> buscarUsuariosPorAceptado(Boolean aceptado) {
		
		return pacienteRepository.findByAceptado(aceptado);
	}


	@Override
	public void eliminarPaciente(Long id) {
		this.pacienteRepository.deleteById(id);
		
	}
	
	
	public PacienteDTOOut buscarPorEmail(String email) {

		return this.pacienteConverter.toDto(this.pacienteRepository.findByEmail(email));
	}


}
