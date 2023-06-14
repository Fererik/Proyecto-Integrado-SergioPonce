package com.tfg.Sergio.services;

import java.util.List;

import com.tfg.Sergio.model.dto.MedicoDTO;
import com.tfg.Sergio.model.dto.MedicoDTOOut;
import com.tfg.Sergio.model.entitys.MedicoEntity;

public interface MedicoService {
    
    List<MedicoDTOOut> buscarTodos();
    
    MedicoDTOOut buscarPorId(Long id);
    
    MedicoDTOOut buscarPorEmail(String email);
    MedicoDTOOut guardar(MedicoDTO medicoDto);
    
    void eliminarPorId(Long id);
    
    MedicoDTOOut modificarInformacion(Long id,MedicoDTO medicoDTO);

	List<MedicoEntity> buscarUsuariosPorAceptado(Boolean aceptado);
    
   // List<CitaDTO> agregarCita(Long idMedico, CitaDTO citaDto);
    
}