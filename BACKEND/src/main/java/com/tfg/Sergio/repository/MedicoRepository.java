package com.tfg.Sergio.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tfg.Sergio.model.entitys.MedicoEntity;
import com.tfg.Sergio.model.entitys.PacienteEntity;

@Repository
public interface MedicoRepository extends JpaRepository<MedicoEntity, Long> {
  
    List<MedicoEntity> findByEspecialidad(String especialidad);
    
	List<MedicoEntity> findByAceptado(boolean aceptado);
	
	MedicoEntity findByEmail(String email);
   
}