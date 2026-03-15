package com.gl.gestionclinique.Service;

import com.gl.gestionclinique.Model.Utilisateur;
import com.gl.gestionclinique.Repository.IUserDAO;
import com.gl.gestionclinique.Utilitaire.Password.PasswordHasher;
import com.gl.gestionclinique.Utilitaire.Session.SessionUtilisateur;

public class AuthService {

    private final IUserDAO dao;

    public AuthService(IUserDAO dao) {
        this.dao = dao;
    }

    public boolean connecter(String email, String motDePasse) {
        Utilisateur utilisateur = dao.findByEmail(email);
        if (utilisateur == null) return false;
        if (!PasswordHasher.verifier(motDePasse, utilisateur.getMotDePasse())) return false;
        SessionUtilisateur.setUtilisateur(utilisateur);
        return true;
    }

    public void inscrire(Utilisateur utilisateur, String motDePasseClair) {
        utilisateur.setMotDePasse(PasswordHasher.hacher(motDePasseClair));
        dao.save(utilisateur);
    }

    public void deconnecter() {
        SessionUtilisateur.clear();
    }
}
