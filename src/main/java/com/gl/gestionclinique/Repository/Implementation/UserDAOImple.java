package com.gl.gestionclinique.Repository.Implementation;

import com.gl.gestionclinique.Model.Utilisateur;
import com.gl.gestionclinique.Repository.IUserDAO;
import com.gl.gestionclinique.config.FactoryJPA;
import javax.persistence.EntityManager;
import java.util.List;

public class UserDAOImple extends GenerateDAOImple<Utilisateur> implements IUserDAO {

    public UserDAOImple() {
        super(Utilisateur.class);
    }

    @Override
    public Utilisateur findByEmail(String email) {
        EntityManager em = FactoryJPA.getManager();
        try {
            return em.createQuery(
                    "FROM Utilisateur u WHERE u.email = :email", Utilisateur.class)
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        } finally {
            em.close();
        }
    }
}
