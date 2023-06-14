package com.tfg.Sergio.services.converters;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.tfg.Sergio.model.dto.UsuarioDTO;
import com.tfg.Sergio.model.dto.UsuarioDTOOut;
import com.tfg.Sergio.model.entitys.UsuarioEntity;

@Component
public class UsuarioConverter {

    public UsuarioDTOOut convertToDto(UsuarioEntity entity) {
    	UsuarioDTOOut dto = new UsuarioDTOOut();
        dto.setNombre(entity.getNombre());
        dto.setApellidos(entity.getApellidos());
        dto.setEmail(entity.getEmail());
//        dto.setAceptado(entity.getAceptado());
        dto.setId(entity.getId());

        return dto;
    }

    public List<UsuarioDTOOut> convertToDtoList(List<UsuarioEntity> entityList) {
        List<UsuarioDTOOut> dtoList = new ArrayList<>();
        for (UsuarioEntity entity : entityList) {
            dtoList.add(convertToDto(entity));
        }
        return dtoList;
    }

    public UsuarioEntity convertToEntity(UsuarioDTO dto) {
        UsuarioEntity entity= new UsuarioEntity();
        entity.setNombre(dto.getNombre());
        entity.setApellidos(dto.getApellidos());
        entity.setEmail(dto.getEmail());
        entity.setPassword(dto.getPassword());
        return entity;
    }

    public List<UsuarioEntity> convertToEntityList(List<UsuarioDTO> dtoList) {
        List<UsuarioEntity> entityList = new ArrayList<>();
        for (UsuarioDTO dto : dtoList) {
            entityList.add(convertToEntity(dto));
        }
        return entityList;
    }
}
