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
import org.springframework.web.bind.annotation.RestController;

import com.tfg.Sergio.model.dto.CitaDTO;
import com.tfg.Sergio.model.dto.CitaDTOOut;
import com.tfg.Sergio.services.impl.CitaServiceImpl;

@RestController
@RequestMapping("/api/citas")
public class CitaController {
    @Autowired
    private  CitaServiceImpl citaService;
    
    @GetMapping()
    public ResponseEntity<List<CitaDTOOut>> getCitas() {
    	List<CitaDTOOut> citaDTO = citaService.getCitas();
        return new ResponseEntity<>(citaDTO, HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<CitaDTOOut> getCitaById(@PathVariable Long id) {
    	CitaDTOOut citaDTO = citaService.getCitaById(id);
        return new ResponseEntity<>(citaDTO, HttpStatus.OK);
    }

    @GetMapping("/paciente/{pacienteId}")
    public ResponseEntity<List<CitaDTOOut>> getCitasByPaciente(@PathVariable Long pacienteId) {
        List<CitaDTOOut> citasDTO = citaService.getCitasByPaciente(pacienteId);
        return new ResponseEntity<>(citasDTO, HttpStatus.OK);
    }

    @GetMapping("/medico/{medicoId}")
    public ResponseEntity<List<CitaDTOOut>> getCitasByMedico(@PathVariable Long medicoId) {
        List<CitaDTOOut> citasDTO = citaService.getCitasByMedico(medicoId);
        return new ResponseEntity<>(citasDTO, HttpStatus.OK);
    }

//    @GetMapping
//    public ResponseEntity<List<CitaDTOOut>> getCitasByFechaBetween(
//    		@DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")  @RequestParam("fechaInicio") Date fechaInicio,
//    		@DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss") @RequestParam("fechaFin")  Date fechaFin) {
//        List<CitaDTOOut> citasDTO = citaService.getCitasByFechaBetween(fechaInicio, fechaFin);
//        return new ResponseEntity<>(citasDTO, HttpStatus.OK);
//    }

    @PostMapping
    public ResponseEntity<CitaDTOOut> reservarCita(
            @RequestBody CitaDTO citaDTO) {
    	CitaDTOOut citaReservada = citaService.crearCita(citaDTO);
        return new ResponseEntity<>(citaReservada, HttpStatus.CREATED);
    }

    @PutMapping("/{citaId}")
    public ResponseEntity<CitaDTOOut> actualizarCita(
            @PathVariable Long citaId,
            @RequestBody CitaDTO citaDTO) {
    	CitaDTOOut citaActualizada = citaService.actualizarCita(citaId, citaDTO);
        return new ResponseEntity<>(citaActualizada, HttpStatus.OK);
    }
    
    @PutMapping("/{citaId}/{pacienteId}")
    public ResponseEntity<CitaDTOOut> enlazarCita(
            @PathVariable Long citaId,
            @PathVariable Long pacienteId) {
    	CitaDTOOut citaActualizada = citaService.enlazarPaciente(pacienteId, citaId);
        return new ResponseEntity<>(citaActualizada, HttpStatus.OK);
    }

    @DeleteMapping("/{citaId}")
    public ResponseEntity<Void> cancelarCita(@PathVariable Long citaId) {
        citaService.cancelarCita(citaId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
