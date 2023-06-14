package com.tfg.Sergio.seguridad;

/**
 * CLASE DTO QUE SE UTILIZA EXCLUSIVAMENTE PARA OBTENER LOS DOS DATOS QUE EL USUARIO METE POR PANTALLA EN EL LOGIN
 */
public class CredencialesInicioSesionDTO {
    private String username;
    private String password;

    public CredencialesInicioSesionDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public CredencialesInicioSesionDTO() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
