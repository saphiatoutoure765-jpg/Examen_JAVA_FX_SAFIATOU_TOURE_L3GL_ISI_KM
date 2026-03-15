package com.gl.gestionclinique.Repository.Implementation;

import com.gl.gestionclinique.Model.Medecin;
import com.gl.gestionclinique.Repository.IMedecinDAO;
import com.gl.gestionclinique.config.FactoryJPA;
import javax.persistence.EntityManager;
import java.util.List;

public class MedecinDAOImple extends GenerateDAOImple<Medecin> implements IMedecinDAO {

    public MedecinDAOImple() {
        super(Medecin.class);
    }

    @Override
    public Medecin findById(int id) {
        EntityManager em = FactoryJPA.getManager();
        Medecin m = em.find(Medecin.class, id);
        em.close();
        return m;
    }

    @Override
    public List<Medecin> findAll() {
        return getAll();
    }
}
