package com.tfg.Sergio.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tfg.Sergio.model.entitys.UsuarioEntity;


@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long> {
	
List<UsuarioEntity> findByAceptado(boolean aceptado);

UsuarioEntity findUsuarioByEmail(String username);
}