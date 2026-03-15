package com.gl.gestionclinique.Utilitaire.Password;

public class TestHash {
    public static void main(String[] args) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest("admin123".getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hash) {
                sb.append(String.format("%02x", b));
            }
            System.out.println("HASH: " + sb.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}