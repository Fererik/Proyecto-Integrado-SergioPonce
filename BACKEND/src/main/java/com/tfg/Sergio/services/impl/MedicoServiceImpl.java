package com.tfg.Sergio.services.impl;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tfg.Sergio.model.dto.MedicoDTO;
import com.tfg.Sergio.model.dto.MedicoDTOOut;
import com.tfg.Sergio.model.entitys.MedicoEntity;
import com.tfg.Sergio.model.entitys.PacienteEntity;
import com.tfg.Sergio.repository.MedicoRepository;
import com.tfg.Sergio.seguridad.Encriptadores;
import com.tfg.Sergio.services.MedicoService;
import com.tfg.Sergio.services.converters.MedicoConverter;

@Service
public class MedicoServiceImpl implements MedicoService {
	
	@Autowired
	private MedicoRepository repository;
	
	@Autowired
	private MedicoConverter converter;
	
	@Autowired
	Encriptadores passwordEncoder;

	@Override
	public List<MedicoDTOOut> buscarTodos() {
		List<MedicoEntity> entidadesList=  this.repository.findAll();
		return this.converter.toDtoList(entidadesList);
	}

	@Override
	public MedicoDTOOut buscarPorId(Long id) {
		MedicoEntity entity = this.repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Medico con id " + id + " no encontrado"));
		
		return this.converter.toDto(entity);
	}

	@Override
	public MedicoDTOOut guardar(MedicoDTO medicoDto) {
		MedicoEntity entidad=this.repository.save(this.converter.toEntity(medicoDto));
		entidad.setPassword(this.passwordEncoder.encode(entidad.getPassword()));
		this.repository.save(entidad);
		return this.converter.toDto(entidad);
	}

	@Override
	public void eliminarPorId(Long id) {
	this.repository.deleteById(id);

	}

	@Override
	public MedicoDTOOut modificarInformacion(Long id,MedicoDTO dto) {
		MedicoEntity entity = this.repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Paciente con id " + id + " no encontrado"));
		 entity.setNombre(dto.getNombre());
	        entity.setApellidos(dto.getApellidos());
	        entity.setEmail(dto.getEmail());
	        entity.setEspecialidad(dto.getEspecialidad());
	      entity=  this.repository.save(entity);
	        
		
		return this.converter.toDto(entity);
	}
	
public List<MedicoEntity> buscarUsuariosPorAceptado(Boolean aceptado) {
		
		return repository.findByAceptado(aceptado);
	}

@Override
public MedicoDTOOut buscarPorEmail(String email) {

	return this.converter.toDto(this.repository.findByEmail(email));
}

}
