package com.gl.gestionclinique.Repository.Implementation;

import com.gl.gestionclinique.Model.Facture;
import com.gl.gestionclinique.Repository.IFactureDAO;
import com.gl.gestionclinique.config.FactoryJPA;
import javax.persistence.EntityManager;
import java.util.List;

public class FactureDAOImple extends GenerateDAOImple<Facture> implements IFactureDAO {

    public FactureDAOImple() {
        super(Facture.class);
    }

    @Override
    public Facture findById(int id) {
        EntityManager em = FactoryJPA.getManager();
        Facture f = em.find(Facture.class, id);
        em.close();
        return f;
    }

    @Override
    public List<Facture> findAll() {
        return getAll();
    }
}
