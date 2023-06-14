package com.tfg.Sergio.model.entitys;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "medicos")
@Data 
@AllArgsConstructor 
@NoArgsConstructor
public class MedicoEntity extends UsuarioEntity {

    
    private String especialidad;

    @OneToMany(mappedBy = "medico")
    private List<CitaEntity> citas;

    // Constructor, getters y setters
}
