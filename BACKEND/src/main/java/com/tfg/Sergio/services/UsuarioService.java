package com.tfg.Sergio.services;

import java.util.List;
import java.util.Optional;

import com.tfg.Sergio.model.dto.UsuarioDTO;
import com.tfg.Sergio.model.entitys.UsuarioEntity;

public interface UsuarioService {
    void aprobarSolicitudAcceso(Long idUsuario);
    void desestimarSolicitudAcceso(Long idUsuario);
    List<UsuarioEntity> buscarUsuariosNoAceptados();
	
  
}