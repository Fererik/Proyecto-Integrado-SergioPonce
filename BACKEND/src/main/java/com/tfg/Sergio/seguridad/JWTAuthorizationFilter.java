package com.tfg.Sergio.seguridad;

import java.io.IOException;
import java.util.Collections;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Component
public class JWTAuthorizationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Obtenemos el token que hay en la petición
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            String token = bearerToken.replace("Bearer ", "");
            // Obtenemos los claims del token firmando este con la clave publica
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(GeneradorClaves.getClavePublica())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            // Obtenemos el sujeto que se usó al generar el token
            String username = claims.getSubject();
            // Obtenemos los roles del usuario
            String rol = claims.get("rol").toString();
            // Verificamos que el usuario tenga los roles necesarios para acceder a la ruta protegid
            if (rol.contains("administrador") || rol.contains("paciente") || rol.contains("medico")) {
                // Creamos un objeto UsernamePasswordAuthenticationToken que representa que el usuario se ha autenticado
                UsernamePasswordAuthenticationToken usernamePAT = new UsernamePasswordAuthenticationToken(username, null, Collections.emptyList());
                // Cambiamos en la cadena de seguridad la autenticación del usuario
                SecurityContextHolder.getContext().setAuthentication(usernamePAT);
            }
        }
        // Continua la cadena de filtros de seguridad
        filterChain.doFilter(request, response);
    }
}
