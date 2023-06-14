package com.tfg.Sergio.model.entitys;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "pacientes")
@Data @AllArgsConstructor @NoArgsConstructor
public class PacienteEntity extends UsuarioEntity {
	
	private String numeroPoliza;

    @OneToMany(mappedBy = "paciente")
    private List<CitaEntity> citas;
    
    
   

	public String getNumeroPoliza() {
		return numeroPoliza;
	}

	public void setNumeroPoliza(String numeroPoliza) {
		this.numeroPoliza = numeroPoliza;
	}

	public List<CitaEntity> getCitas() {
		return citas;
	}

	public void setCitas(List<CitaEntity> citas) {
		this.citas = citas;
	}

    // Constructor, getters y setters
}

