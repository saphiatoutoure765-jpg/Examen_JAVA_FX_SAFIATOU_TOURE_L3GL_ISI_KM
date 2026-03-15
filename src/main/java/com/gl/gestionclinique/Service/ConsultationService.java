package com.gl.gestionclinique.Service;

import com.gl.gestionclinique.Model.Consultation;
import com.gl.gestionclinique.Model.Patient;
import com.gl.gestionclinique.Model.StatutRendezVous;
import com.gl.gestionclinique.Repository.IConsultationDAO;
import java.time.LocalDate;
import java.util.List;

public class ConsultationService {

    private final IConsultationDAO dao;

    public ConsultationService(IConsultationDAO dao) {
        this.dao = dao;
    }

    public void enregistrer(Consultation consultation) {
        consultation.setDate(LocalDate.now());
        if (consultation.getRendezVous() != null) {
            consultation.getRendezVous().setStatut(StatutRendezVous.TERMINE);
        }
        dao.save(consultation);
    }

    public void modifier(Consultation consultation) {
        dao.update(consultation);
    }

    public List<Consultation> toutes() {
        return dao.getAll();
    }

    public List<Consultation> parPatient(Patient patient) {
        return dao.findByPatient(patient);
    }

    public Consultation trouverParId(int id) {
        return dao.findById(id);
    }
}
