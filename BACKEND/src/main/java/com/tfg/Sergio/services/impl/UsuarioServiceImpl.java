package com.tfg.Sergio.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.tfg.Sergio.model.entitys.UsuarioEntity;
import com.tfg.Sergio.repository.UsuarioRepository;
import com.tfg.Sergio.seguridad.UserDetailsImpl;
import com.tfg.Sergio.services.UsuarioService;

@Service
public class UsuarioServiceImpl implements UsuarioService,UserDetailsService {
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    


    @Override
    public void aprobarSolicitudAcceso(Long idUsuario) {
        Optional<UsuarioEntity> usuario = usuarioRepository.findById(idUsuario);
        if (usuario.isPresent()) {
            usuario.get().setAceptado(true);
            usuarioRepository.save(usuario.get());
        } else {
            throw new RuntimeException("El usuario con id " + idUsuario + " no existe.");
        }
    }

    @Override
    public void desestimarSolicitudAcceso(Long idUsuario) {
        Optional<UsuarioEntity> usuario = usuarioRepository.findById(idUsuario);
        if (usuario.isPresent()) {
            usuarioRepository.delete(usuario.get());
        } else {
            throw new RuntimeException("El usuario con id " + idUsuario + " no existe.");
        }
    }

    @Override
    public List<UsuarioEntity> buscarUsuariosNoAceptados() {
        return usuarioRepository.findByAceptado(false);
    }

	public List<UsuarioEntity> buscarUsuariosPorAceptado(Boolean aceptado) {
		
		return usuarioRepository.findByAceptado(aceptado);
	}

	public List<UsuarioEntity> listarTodosLosUsuarios() {
		
		return  usuarioRepository.findAll();
	}

	public Optional<UsuarioEntity> buscarUsuarioPorId(Long id) {

		return usuarioRepository.findById(id);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		 UsuarioEntity usuario = this.usuarioRepository.findUsuarioByEmail(username);
	        return new UserDetailsImpl(usuario);
	}

	

   
}