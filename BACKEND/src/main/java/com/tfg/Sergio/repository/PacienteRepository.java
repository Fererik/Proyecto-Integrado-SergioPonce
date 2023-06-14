package com.tfg.Sergio.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tfg.Sergio.model.entitys.MedicoEntity;
import com.tfg.Sergio.model.entitys.PacienteEntity;
import com.tfg.Sergio.model.entitys.UsuarioEntity;

@Repository
public interface PacienteRepository extends JpaRepository<PacienteEntity, Long> {
	List<PacienteEntity> findByAceptado(boolean aceptado);
	
	PacienteEntity findByEmail(String email);
}