package com.gl.gestionclinique.Repository;

import com.gl.gestionclinique.Model.Utilisateur;

public interface IUserDAO extends GenericDAO<Utilisateur> {
    Utilisateur findByEmail(String email);
}
