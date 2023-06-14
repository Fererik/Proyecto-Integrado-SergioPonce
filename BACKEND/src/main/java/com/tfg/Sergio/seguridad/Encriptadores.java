package com.tfg.Sergio.seguridad;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
/**
 * Componente usado para encriptar las contraseñas recogidas por el usuario
 *
 */
@Component
public class Encriptadores implements PasswordEncoder {
    private static final String CLAVE = "claveSecreta1234"; // Clave para encriptar y desencriptar, debe tener 16 bytes
    
    /**
     * Metodo que encripta la contraseña.
     * @param String texto, el cual se va a encryptar
     * @return String cadena de texto encriptada
     */
    public String encriptar(String texto) {
        String cadenaTextoEncriptada = null;
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            byte[] claveEnBytes = CLAVE.getBytes(StandardCharsets.UTF_8);
            SecretKeySpec claveSecreta = new SecretKeySpec(claveEnBytes, "AES");
            cipher.init(Cipher.ENCRYPT_MODE, claveSecreta);
            byte[] textoEnBytes = texto.getBytes(StandardCharsets.UTF_8);
            byte[] textoEncriptado = cipher.doFinal(textoEnBytes);
            cadenaTextoEncriptada = Base64.getEncoder().encodeToString(textoEncriptado);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cadenaTextoEncriptada;
    }
    /**
     * Metodo que desencripta la contraseña.
     * @param String texto, el cual se va a desencryptar
     * @return String contraseña.
     */
    public String desencriptar(String textoEncriptado) {
        String cadenaTextoDesencriptada = null;
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            byte[] claveEnBytes = CLAVE.getBytes(StandardCharsets.UTF_8);
            SecretKeySpec claveSecreta = new SecretKeySpec(claveEnBytes, "AES");
            cipher.init(Cipher.DECRYPT_MODE, claveSecreta);
            byte[] textoEnBytes = Base64.getDecoder().decode(textoEncriptado);
            byte[] textoDesencriptado = cipher.doFinal(textoEnBytes);
            cadenaTextoDesencriptada = new String(textoDesencriptado, StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cadenaTextoDesencriptada;
    }
    	/**
    	 * Necesario debido a implments PasswordEncoder para encripitar
    	 */
    @Override
    public String encode(CharSequence rawPassword) {
        return encriptar(rawPassword.toString());
    }
    /**
	 * Necesario debido a implments PasswordEncoder para comparar 
	 */
    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return encodedPassword.equals(encriptar(rawPassword.toString()));
    }
}
