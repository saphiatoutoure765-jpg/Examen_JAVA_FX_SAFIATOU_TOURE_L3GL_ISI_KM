package com.gl.gestionclinique.Utilitaire.Session;

import com.gl.gestionclinique.Model.Utilisateur;
import com.gl.gestionclinique.Model.RoleUser;

public class SessionUtilisateur {

    private static Utilisateur utilisateurConnecte;

    private SessionUtilisateur() {}

    public static void setUtilisateur(Utilisateur u) {
        utilisateurConnecte = u;
    }

    public static Utilisateur getUtilisateur() {
        return utilisateurConnecte;
    }

    public static String getNomComplet() {
        if (utilisateurConnecte == null) return "";
        return utilisateurConnecte.getPrenom() + " " + utilisateurConnecte.getNom();
    }

    public static RoleUser getRole() {
        if (utilisateurConnecte == null) return null;
        return utilisateurConnecte.getRole();
    }

    public static boolean isConnecte() {
        return utilisateurConnecte != null;
    }

    public static boolean isAdmin() {
        return isConnecte() && utilisateurConnecte.getRole() == RoleUser.ADMIN;
    }

    public static boolean isMedecin() {
        return isConnecte() && utilisateurConnecte.getRole() == RoleUser.MEDECIN;
    }

    public static boolean isReceptionniste() {
        return isConnecte() && utilisateurConnecte.getRole() == RoleUser.RECEPTIONNISTE;
    }

    public static void clear() {
        utilisateurConnecte = null;
    }
}
