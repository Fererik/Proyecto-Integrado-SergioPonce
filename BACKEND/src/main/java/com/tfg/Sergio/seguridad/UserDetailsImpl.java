package com.tfg.Sergio.seguridad;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.tfg.Sergio.model.entitys.MedicoEntity;
import com.tfg.Sergio.model.entitys.PacienteEntity;
import com.tfg.Sergio.model.entitys.UsuarioEntity;

/**
 * Implmentacion de UserDetails, contiene metodos para la autentificacion de usuario.
 */
public class UserDetailsImpl implements UserDetails {
    private UsuarioEntity usuario;

    public UserDetailsImpl(UsuarioEntity usuario) {
        this.usuario = usuario;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public String getPassword() {
        return usuario.getPassword();
    }

    @Override
    public String getUsername() {
        return usuario.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public String getNombre() {
        return usuario.getNombre() + " " + usuario.getApellidos();
    }

    public String getRol() {
        String rol = null;
        if (usuario instanceof PacienteEntity) {
            rol = "paciente";
        } else if (usuario instanceof MedicoEntity) {
            rol = "medico";
        } else if (usuario instanceof UsuarioEntity) {
            rol = "administrador";
        }
        return rol;
    }
    
    public Boolean getAceptado() {
    	return usuario.getAceptado();
    }
}
