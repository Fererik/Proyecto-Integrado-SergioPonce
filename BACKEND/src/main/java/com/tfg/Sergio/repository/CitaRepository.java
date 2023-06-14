package com.tfg.Sergio.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tfg.Sergio.model.entitys.CitaEntity;

@Repository
public interface CitaRepository extends JpaRepository<CitaEntity, Long> {
	 Optional<List<CitaEntity>> findByPacienteId(Long paciente_id);
   Optional<List<CitaEntity>> findByMedicoId(Long medico_id);
    List<CitaEntity> findByFechaBetween(Date fechaInicio, Date fechaFin);
}