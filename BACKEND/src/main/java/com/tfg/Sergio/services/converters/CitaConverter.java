package com.tfg.Sergio.services.converters;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tfg.Sergio.model.dto.CitaDTO;
import com.tfg.Sergio.model.dto.CitaDTOOut;
import com.tfg.Sergio.model.entitys.CitaEntity;
import com.tfg.Sergio.repository.MedicoRepository;
@Component
public class CitaConverter {
	
	@Autowired
	MedicoRepository medicoRepository;
	

	public CitaDTOOut toDto(CitaEntity entity) {
		CitaDTOOut dto = new CitaDTOOut();
        dto.setId(entity.getId());
        dto.setFecha(entity.getFecha());
        dto.setMedico_id(entity.getMedico().getId());
        if(entity.getPaciente()!=null)
        dto.setPaciente_id(entity.getPaciente().getId());
        dto.setEspecialidad(entity.getEspecialidad());

        return dto;
    }
	
	 public CitaEntity toEntity(CitaDTO dto) {
		 CitaEntity entity= new CitaEntity();
	       entity.setFecha(dto.getFecha());
	       entity.setEspecialidad(dto.getEspecialidad());
	       entity.setMedico(this.medicoRepository.findById(dto.getMedico_id()).orElseThrow(() -> new EntityNotFoundException("Cita con id " + dto.getMedico_id() + " no encontrado")));
	        
	        return entity;
	    }
	 
	 
	 public List<CitaDTOOut> toDtoList(List<CitaEntity> entityList) {
	        List<CitaDTOOut> dtoList = new ArrayList<>();
	        for (CitaEntity entity : entityList) {
	            dtoList.add(toDto(entity));
	        }
	        return dtoList;
	    }
	 
	 
	 public List<CitaEntity> toEntityList(List<CitaDTO> dtoList) {
	        List<CitaEntity> entityList = new ArrayList<>();
	        for (CitaDTO dto : dtoList) {
	            entityList.add(toEntity(dto));
	        }
	        return entityList;
	    }


}
