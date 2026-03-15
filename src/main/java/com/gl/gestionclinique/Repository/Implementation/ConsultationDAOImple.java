package com.gl.gestionclinique.Repository.Implementation;

import com.gl.gestionclinique.Model.Consultation;
import com.gl.gestionclinique.Model.Patient;
import com.gl.gestionclinique.Repository.IConsultationDAO;
import com.gl.gestionclinique.config.FactoryJPA;
import javax.persistence.EntityManager;
import java.util.List;

public class ConsultationDAOImple extends GenerateDAOImple<Consultation> implements IConsultationDAO {

    public ConsultationDAOImple() {
        super(Consultation.class);
    }

    @Override
    public Consultation findById(int id) {
        EntityManager em = FactoryJPA.getManager();
        Consultation c = em.find(Consultation.class, id);
        em.close();
        return c;
    }

    @Override
    public List<Consultation> findByPatient(Patient patient) {
        EntityManager em = FactoryJPA.getManager();
        List<Consultation> result = em.createQuery(
                "FROM Consultation c WHERE c.patient = :patient", Consultation.class)
                .setParameter("patient", patient)
                .getResultList();
        em.close();
        return result;
    }
}
