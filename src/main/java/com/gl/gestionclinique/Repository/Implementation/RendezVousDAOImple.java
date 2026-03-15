package com.gl.gestionclinique.Repository.Implementation;

import com.gl.gestionclinique.Model.Medecin;
import com.gl.gestionclinique.Model.RendezVous;
import com.gl.gestionclinique.Repository.IRendezVousDAO;
import com.gl.gestionclinique.config.FactoryJPA;
import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

public class RendezVousDAOImple extends GenerateDAOImple<RendezVous> implements IRendezVousDAO {

    public RendezVousDAOImple() {
        super(RendezVous.class);
    }

    @Override
    public RendezVous findById(int id) {
        EntityManager em = FactoryJPA.getManager();
        RendezVous rv = em.find(RendezVous.class, id);
        em.close();
        return rv;
    }

    @Override
    public List<RendezVous> findByMedecin(Medecin medecin) {
        EntityManager em = FactoryJPA.getManager();
        List<RendezVous> result = em.createQuery(
                "FROM RendezVous r WHERE r.medecin = :medecin", RendezVous.class)
                .setParameter("medecin", medecin)
                .getResultList();
        em.close();
        return result;
    }

    @Override
    public List<RendezVous> findByDate(LocalDateTime date) {
        EntityManager em = FactoryJPA.getManager();
        List<RendezVous> result = em.createQuery(
                "FROM RendezVous r WHERE DATE(r.dateHeure) = DATE(:date)", RendezVous.class)
                .setParameter("date", date)
                .getResultList();
        em.close();
        return result;
    }

    @Override
    public boolean existsConflict(Medecin medecin, LocalDateTime dateHeure) {
        EntityManager em = FactoryJPA.getManager();
        Long count = em.createQuery(
                "SELECT COUNT(r) FROM RendezVous r WHERE r.medecin = :medecin AND r.dateHeure = :dateHeure AND r.statut <> 'ANNULE'", Long.class)
                .setParameter("medecin", medecin)
                .setParameter("dateHeure", dateHeure)
                .getSingleResult();
        em.close();
        return count > 0;
    }
}
