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

import com.tfg.Sergio.model.dto.MedicoDTO;
import com.tfg.Sergio.model.dto.MedicoDTOOut;
import com.tfg.Sergio.model.dto.PacienteDTOOut;
import com.tfg.Sergio.services.MedicoService;
import com.tfg.Sergio.services.converters.MedicoConverter;

@RestController
@RequestMapping("/api/medicos")
public class MedicoController {
    
	@Autowired
    private  MedicoService medicoService;
	
	@Autowired
	private MedicoConverter medicoConverter;

  
    
    @PostMapping
    public ResponseEntity<MedicoDTOOut> crearMedico(@RequestBody MedicoDTO medicoDTO) {
    	MedicoDTOOut medicoCreado = medicoService.guardar(medicoDTO);
        return new ResponseEntity<>(medicoCreado, HttpStatus.CREATED);
    }
    
    @GetMapping("id/{id}")
    public ResponseEntity<MedicoDTOOut> buscarMedicoPorId(@PathVariable Long id) {
    	MedicoDTOOut medicoEncontrado = medicoService.buscarPorId(id);
        return new ResponseEntity<>(medicoEncontrado, HttpStatus.OK);
    }
    
    @GetMapping("email/{email}")
    public ResponseEntity<MedicoDTOOut> buscarMedicoPorEmail(@PathVariable String email) {
    	MedicoDTOOut medicoEncontrado = medicoService.buscarPorEmail(email);
        return new ResponseEntity<>(medicoEncontrado, HttpStatus.OK);
    }
    

    
    @GetMapping
    public List<MedicoDTOOut> listarUsuarios(@RequestParam(required = false) Boolean aceptado) {
        if (aceptado != null) {
            return medicoConverter.toDtoList(medicoService.buscarUsuariosPorAceptado(aceptado));
        } else {
            return medicoService.buscarTodos();
        }
    }
    
    
    @PutMapping("/{id}")
    public ResponseEntity<MedicoDTOOut> actualizarMedico(@PathVariable Long id, @RequestBody MedicoDTO medicoDTO) {
    	MedicoDTOOut medicoActualizado = medicoService.modificarInformacion(id, medicoDTO);
        return new ResponseEntity<>(medicoActualizado, HttpStatus.OK);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> borrarMedico(@PathVariable Long id) {
        medicoService.eliminarPorId(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
   
}