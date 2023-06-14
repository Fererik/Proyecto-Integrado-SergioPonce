package com.tfg.Sergio.seguridad;

import java.io.IOException;
import java.util.Collections;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;


public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    /**
     * Realiza la autenticación del usuario
     *
     * @param request  Solicitud HTTP que contiene las credenciales de inicio de sesión del usuario en formato JSON
     * @param response
     * @return
     * @throws AuthenticationException
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        CredencialesInicioSesionDTO credencialesInicioSesionDTO = new CredencialesInicioSesionDTO();
        try {
            // Obtenemos los datos del json y lo convertimos a objeto DTO
            credencialesInicioSesionDTO = new ObjectMapper().readValue(request.getReader(), CredencialesInicioSesionDTO.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Creamos el objeto que usará spring para autenticar al usuario
        UsernamePasswordAuthenticationToken usernamePAT = new UsernamePasswordAuthenticationToken(
                credencialesInicioSesionDTO.getUsername(),
                credencialesInicioSesionDTO.getPassword(),
                Collections.emptyList()
        );
        return getAuthenticationManager().authenticate(usernamePAT);
    }

    /**
     * Agrega el token a la petición HTTP cuando la autenticación es exitosa
     *
     * @param request
     * @param response
     * @param chain
     * @param authResult el resultado que se dió al intentar autenticar al usuario
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        // Obtiene al usuario que se acaba de autenticar
        UserDetailsImpl userDetails = (UserDetailsImpl) authResult.getPrincipal();
        // Creamos el token
        String token = UtilidadesToken.createToken(userDetails.getNombre(), userDetails.getUsername(), userDetails.getRol(), userDetails.getAceptado());
        // Añadimos el token a al header de la petición
        response.addHeader("Authorization", "Bearer " + token);
        
        // Envíamos la petición HTTP
        response.getWriter().flush();
        super.successfulAuthentication(request, response, chain, authResult);
    }
}
