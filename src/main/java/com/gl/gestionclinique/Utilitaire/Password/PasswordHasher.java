package com.gl.gestionclinique.Utilitaire.Password;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordHasher {

    private PasswordHasher() {}

    public static String hacher(String motDePasseClair) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(motDePasseClair.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hash) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean verifier(String motDePasseClair, String motDePasseHache) {
        return hacher(motDePasseClair).equals(motDePasseHache);
    }
}