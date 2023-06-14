package com.tfg.Sergio.services.converters;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.tfg.Sergio.model.dto.PacienteDTO;
import com.tfg.Sergio.model.dto.PacienteDTOOut;
import com.tfg.Sergio.model.entitys.PacienteEntity;

@Component
public class PacienteConverter {
	
	
	public  PacienteDTOOut toDto(PacienteEntity entity) {
		PacienteDTOOut dto = new PacienteDTOOut();
		dto.setId(entity.getId());
        dto.setNombre(entity.getNombre());
        dto.setApellidos(entity.getApellidos());
        dto.setEmail(entity.getEmail());
        dto.setAceptado(entity.getAceptado());
        dto.setNumPoliza(entity.getNumeroPoliza());

        return dto;
    }
	
	 public PacienteEntity toEntity(PacienteDTO dto) {
		 PacienteEntity entity= new PacienteEntity();
	        entity.setNombre(dto.getNombre());
	        entity.setApellidos(dto.getApellidos());
	        entity.setEmail(dto.getEmail());
//	        entity.setAceptado(dto.isAceptado());
	        entity.setNumeroPoliza(dto.getNumPoliza());
	        entity.setPassword(dto.getPassword());
	        return entity;
	    }
	 
	 
	 public  List<PacienteDTOOut> toDtoList(List<PacienteEntity> entityList) {
	        List<PacienteDTOOut> dtoList = new ArrayList<>();
	        for (PacienteEntity entity : entityList) {
	            dtoList.add(toDto(entity));
	        }
	        return dtoList;
	    }
	 
	 
	 public List<PacienteEntity> toEntityList(List<PacienteDTO> dtoList) {
	        List<PacienteEntity> entityList = new ArrayList<>();
	        for (PacienteDTO dto : dtoList) {
	            entityList.add(toEntity(dto));
	        }
	        return entityList;
	    }


}
