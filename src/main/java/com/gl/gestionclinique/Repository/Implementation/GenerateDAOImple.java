package com.gl.gestionclinique.Repository.Implementation;

import com.gl.gestionclinique.Repository.GenericDAO;
import com.gl.gestionclinique.config.FactoryJPA;
import javax.persistence.EntityManager;
import java.util.List;

public class GenerateDAOImple<T> implements GenericDAO<T> {

    private Class<T> type;

    public GenerateDAOImple(Class<T> type) {
        this.type = type;
    }

    @Override
    public void save(T entite) {
        EntityManager em = FactoryJPA.getManager();
        em.getTransaction().begin();
        em.persist(entite);
        em.getTransaction().commit();
        em.close();
    }

    @Override
    public void update(T entite) {
        EntityManager em = FactoryJPA.getManager();
        em.getTransaction().begin();
        em.merge(entite);
        em.getTransaction().commit();
        em.close();
    }

    @Override
    public void delete(T entite) {
        EntityManager em = FactoryJPA.getManager();
        em.getTransaction().begin();
        em.remove(em.merge(entite));
        em.getTransaction().commit();
        em.close();
    }

    @Override
    public List<T> getAll() {
        EntityManager em = FactoryJPA.getManager();
        List<T> result = em.createQuery("FROM " + type.getSimpleName(), type).getResultList();
        em.close();
        return result;
    }
}
