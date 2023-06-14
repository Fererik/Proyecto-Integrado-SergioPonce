package com.tfg.Sergio;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.tfg.Sergio.seguridad.Encriptadores;
import com.tfg.Sergio.seguridad.JWTAuthenticationFilter;
import com.tfg.Sergio.seguridad.JWTAuthorizationFilter;

@Configuration
public class WebSecurityConf {

    private final UserDetailsService USER_DETAIL_SERVICE;
    private final JWTAuthorizationFilter JWT_AUTH_FILTER;

    public WebSecurityConf(UserDetailsService USER_DETAIL_SERVICE, JWTAuthorizationFilter jwtAuthFilter) {
        this.USER_DETAIL_SERVICE = USER_DETAIL_SERVICE;
        JWT_AUTH_FILTER = jwtAuthFilter;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, AuthenticationManager authManager) throws Exception {
        JWTAuthenticationFilter jwtAuthenticationFilter = new JWTAuthenticationFilter();
        // Establecemos el autenticador y la url para autenticar a los usuario
        jwtAuthenticationFilter.setAuthenticationManager(authManager);
        jwtAuthenticationFilter.setFilterProcessesUrl("/login");
        return httpSecurity
                .cors()
                .and()
                .csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers(new AntPathRequestMatcher("/swagger-ui/**"), new AntPathRequestMatcher("/v3/**")).permitAll()
                .antMatchers(HttpMethod.POST, "/api/pacientes").permitAll()
                .antMatchers(HttpMethod.POST, "/api/medicos").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(jwtAuthenticationFilter)
                .addFilterBefore(JWT_AUTH_FILTER, UsernamePasswordAuthenticationFilter.class)
                .headers().frameOptions().disable()
                .and()
                .build();
    }

    /**
     * Configuración del authenticationManager de Spring security
     *
     * @param httpSecurity objeto que utiliza spring para configurar la seguridad
     * @return Un objeto httpSecurity con el manager configurado
     * @throws Exception
     */
    @Bean
    AuthenticationManager authenticationManager(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.getSharedObject(AuthenticationManagerBuilder.class)
                // Establecemos el servicio que se utilizará para cargar los datos del usuario
                .userDetailsService(USER_DETAIL_SERVICE)
                .passwordEncoder(new Encriptadores())
                .and()
                .build();
    }
}
