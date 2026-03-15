package com.gl.gestionclinique.Repository.Implementation;

import com.gl.gestionclinique.Model.Patient;
import com.gl.gestionclinique.Repository.IPatientDAO;
import com.gl.gestionclinique.config.FactoryJPA;
import javax.persistence.EntityManager;
import java.util.List;

public class PatientDAOImple extends GenerateDAOImple<Patient> implements IPatientDAO {

    public PatientDAOImple() {
        super(Patient.class);
    }

    @Override
    public Patient findById(int id) {
        EntityManager em = FactoryJPA.getManager();
        Patient p = em.find(Patient.class, id);
        em.close();
        return p;
    }

    @Override
    public List<Patient> findByNom(String nom) {
        EntityManager em = FactoryJPA.getManager();
        List<Patient> result = em.createQuery(
                "FROM Patient p WHERE LOWER(p.nom) LIKE LOWER(:nom)", Patient.class)
                .setParameter("nom", "%" + nom + "%")
                .getResultList();
        em.close();
        return result;
    }
}
