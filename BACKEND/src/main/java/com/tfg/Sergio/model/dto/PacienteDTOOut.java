package com.tfg.Sergio.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class PacienteDTOOut extends UsuarioDTOOut {
	String numPoliza;
}
