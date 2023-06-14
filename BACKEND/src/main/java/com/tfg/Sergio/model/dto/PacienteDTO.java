package com.tfg.Sergio.model.dto;

import java.util.Date;

import com.tfg.Sergio.model.entitys.MedicoEntity;
import com.tfg.Sergio.model.entitys.PacienteEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class PacienteDTO extends UsuarioDTO {
	String numPoliza;
}
