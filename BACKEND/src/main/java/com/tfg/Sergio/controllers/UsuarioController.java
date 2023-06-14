package com.tfg.Sergio.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tfg.Sergio.model.dto.UsuarioDTOOut;
import com.tfg.Sergio.services.converters.UsuarioConverter;
import com.tfg.Sergio.services.impl.UsuarioServiceImpl;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {
    @Autowired
    private UsuarioServiceImpl usuarioService;
    
    @Autowired 
    UsuarioConverter usuarioConverter;
 
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTOOut> obtenerUsuarioPorId(@PathVariable Long id) {
    	UsuarioDTOOut usuarioDTO = usuarioConverter.convertToDto(usuarioService.buscarUsuarioPorId(id).get());
        if (usuarioDTO != null) {
            return ResponseEntity.ok(usuarioDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping
    public List<UsuarioDTOOut> listarUsuarios(@RequestParam(required = false) Boolean aceptado) {
        if (aceptado != null) {
            return usuarioConverter.convertToDtoList(usuarioService.buscarUsuariosPorAceptado(aceptado));
        } else {
            return usuarioConverter.convertToDtoList(usuarioService.listarTodosLosUsuarios());
        }
    }
    
    

    @PutMapping("/{id}")
    public ResponseEntity<?> cambiarEstado(@PathVariable Long id ,@RequestParam(required = false) Boolean aceptado) {
    	if (aceptado) {
			this.usuarioService.aprobarSolicitudAcceso(id);
			return ResponseEntity.ok().build();
		}else {
			this.usuarioService.desestimarSolicitudAcceso(id);
			return ResponseEntity.ok().build();
		}
    	
    }
    

    
    
   
}
