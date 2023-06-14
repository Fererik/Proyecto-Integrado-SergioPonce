package com.tfg.Sergio.services.converters;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.tfg.Sergio.model.dto.MedicoDTO;
import com.tfg.Sergio.model.dto.MedicoDTOOut;
import com.tfg.Sergio.model.entitys.MedicoEntity;

@Component
public class MedicoConverter {
	
	
	public MedicoDTOOut toDto(MedicoEntity entity) {
		MedicoDTOOut dto = new MedicoDTOOut();
		dto.setId(entity.getId());
        dto.setNombre(entity.getNombre());
        dto.setApellidos(entity.getApellidos());
        dto.setEmail(entity.getEmail());
        dto.setAceptado(entity.getAceptado());
        dto.setEspecialidad(entity.getEspecialidad());

        return dto;
    }
	
	 public MedicoEntity toEntity(MedicoDTO dto) {
		 MedicoEntity entity= new MedicoEntity();
	        entity.setNombre(dto.getNombre());
	        entity.setApellidos(dto.getApellidos());
	        entity.setEmail(dto.getEmail());
//	        entity.setAceptado(dto.isAceptado());
	       entity.setEspecialidad(dto.getEspecialidad());
	        entity.setPassword(dto.getPassword());
	        return entity;
	    }
	 
	 
	 public List<MedicoDTOOut> toDtoList(List<MedicoEntity> entityList) {
	        List<MedicoDTOOut> dtoList = new ArrayList<>();
	        for (MedicoEntity entity : entityList) {
	            dtoList.add(toDto(entity));
	        }
	        return dtoList;
	    }
	 
	 
	 public List<MedicoEntity> toEntityList(List<MedicoDTO> dtoList) {
	        List<MedicoEntity> entityList = new ArrayList<>();
	        for (MedicoDTO dto : dtoList) {
	            entityList.add(toEntity(dto));
	        }
	        return entityList;
	    }


}
