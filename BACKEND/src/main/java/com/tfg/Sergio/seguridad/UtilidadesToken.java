package com.tfg.Sergio.seguridad;

import io.jsonwebtoken.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.*;

public class UtilidadesToken {
    private static final long VALIDEZ_TOKEN = 2592000;

    /**
     * Crea el token del usuario
     *
     * @param nombre nombre del usuario
     * @param email  email del usuario
     * @return el token
     */
    public static String createToken(String nombre, String email, String rol, Boolean aceptado) {
        JwtBuilder token = null;
        try {
            // Establecemos fecha de expiración
            long expirationTime = VALIDEZ_TOKEN * 1000;
            Date expirationDate = new Date(System.currentTimeMillis() + expirationTime);
            // Metemos en el claim el nombre del usuario
            Map<String, Object> extra = new HashMap<>();
            // Añadimos los extras (nombre y rol)
            extra.put("nombre", nombre);
            extra.put("rol", rol);
            extra.put("aceptado",aceptado.toString());
            // Generamos el token con todos sus datos
            token = Jwts.builder()
                    .setSubject(email)
                    .setExpiration(expirationDate)
                    .addClaims(extra)
                    .signWith(GeneradorClaves.getClavePrivada(), SignatureAlgorithm.ES256);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        // Devolvemos el token con el claim y el sujeto seteado
        return token.compact();
    }

    /**
     * Valida y obtiene la autenticación del usuario
     *
     * @param token el token a validar
     * @return un objeto UsernamePasswordAuthenticationToken que representa el usuario autenticado o null
     */
    public static UsernamePasswordAuthenticationToken getAuthentication(String token) {
        UsernamePasswordAuthenticationToken userPassAuthToken;
        try {
            // Obtenemos el claim del usuario, a través de la clave privada que se utilizó para firmar el token (FIRMA_TOKEN)
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(GeneradorClaves.getClavePublica())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            // Obtenemos el sujeto que se usó al generar el token
            String username = claims.getSubject();
            // Obtenemos la lista de roles
            List<String> roles = (List<String>) claims.get("rol");
            List<GrantedAuthority> authorities = new ArrayList<>();
            for (String role : roles) {
                authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
            }
            // Establecemos un objeto UsernamePasswordAuthenticationToken que representa que el usuario se ha autenticado
            userPassAuthToken = new UsernamePasswordAuthenticationToken(username, null, authorities);
        } catch (JwtException e) {
            userPassAuthToken = null;
        }
        return userPassAuthToken;
    }
}
