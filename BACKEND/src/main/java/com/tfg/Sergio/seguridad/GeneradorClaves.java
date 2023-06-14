package com.tfg.Sergio.seguridad;


import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class GeneradorClaves {
    private static final String ALGORITMO = "EC";
    private static final KeyPair PAR_CLAVES = generarParClaves();

    /**
     * Genera el par de claves
     *
     * @return el par de claves
     */
    private static KeyPair generarParClaves() {
        KeyPairGenerator keyPairGenerator = null;
        try {
            keyPairGenerator = KeyPairGenerator.getInstance(ALGORITMO);
            keyPairGenerator.initialize(256);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        return keyPairGenerator.generateKeyPair();
    }

    /**
     * Genera a partir de el par de claves la clave primaria
     *
     * @return la clave primaria
     */
    public static PrivateKey getClavePrivada() {
        PrivateKey clavePrivada = null;
        try {
            byte[] clavePrivadaBytes = PAR_CLAVES.getPrivate().getEncoded();
            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(clavePrivadaBytes);
            KeyFactory keyFactory = KeyFactory.getInstance(ALGORITMO);
            clavePrivada = keyFactory.generatePrivate(spec);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
        return clavePrivada;
    }

    /**
     * Genera a partir del par de claves la clave publica
     *
     * @return la clave publica
     */
    public static PublicKey getClavePublica() {
        PublicKey clavePublica = null;
        try {
            byte[] clavePublicaBytes = PAR_CLAVES.getPublic().getEncoded();
            X509EncodedKeySpec spec = new X509EncodedKeySpec(clavePublicaBytes);
            KeyFactory keyFactory = KeyFactory.getInstance(ALGORITMO);
            clavePublica = keyFactory.generatePublic(spec);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
        return clavePublica;
    }
}
