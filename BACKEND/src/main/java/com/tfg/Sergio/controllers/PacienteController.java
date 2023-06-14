package com.tfg.Sergio.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tfg.Sergio.model.dto.PacienteDTO;
import com.tfg.Sergio.model.dto.PacienteDTOOut;
import com.tfg.Sergio.model.dto.UsuarioDTOOut;
import com.tfg.Sergio.services.converters.PacienteConverter;
import com.tfg.Sergio.services.impl.PacienteServiceImpl;

@RestController
@RequestMapping("/api/pacientes")
public class PacienteController {

    @Autowired
    private PacienteServiceImpl pacienteService;
    
    @Autowired
    private PacienteConverter pacienteConverter;
    
    
    @GetMapping
    public List<PacienteDTOOut> listarUsuarios(@RequestParam(required = false) Boolean aceptado) {
        if (aceptado != null) {
            return pacienteConverter.toDtoList(pacienteService.buscarUsuariosPorAceptado(aceptado));
        } else {
            return pacienteService.obtenerPacientes();
        }
    }
    

    @GetMapping("id/{id}")
    public ResponseEntity<PacienteDTOOut> getPacienteById(@PathVariable Long id) {
        PacienteDTOOut paciente = pacienteService.obtenerPacientePorId(id);
        return new ResponseEntity<>(paciente, HttpStatus.OK);
    }
    
    @GetMapping("email/{email}")
    public ResponseEntity<PacienteDTOOut> getPacienteByEmail(@PathVariable String email) {
        PacienteDTOOut paciente = pacienteService.buscarPorEmail(email);
        return new ResponseEntity<>(paciente, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<PacienteDTOOut> addPaciente(@RequestBody PacienteDTO pacienteDTO) {
    	PacienteDTOOut pacienteCreado = pacienteService.crearPaciente(pacienteDTO);
        return new ResponseEntity<>(pacienteCreado, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PacienteDTOOut> updatePaciente(@PathVariable Long id, @RequestBody PacienteDTO pacienteDTO) {
        PacienteDTOOut pacienteActualizado = pacienteService.actualizarPaciente(id, pacienteDTO);
        return new ResponseEntity<>(pacienteActualizado, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePaciente(@PathVariable Long id) {
        pacienteService.eliminarPaciente(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
