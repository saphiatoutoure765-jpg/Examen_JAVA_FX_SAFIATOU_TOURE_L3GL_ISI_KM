package com.gl.gestionclinique.Service;

import com.gl.gestionclinique.Model.Patient;
import com.gl.gestionclinique.Repository.IPatientDAO;
import java.util.List;

public class PatientService {

    private final IPatientDAO dao;

    public PatientService(IPatientDAO dao) {
        this.dao = dao;
    }

    public void ajouterPatient(Patient patient) {
        dao.save(patient);
    }

    public void modifierPatient(Patient patient) {
        dao.update(patient);
    }

    public void supprimerPatient(Patient patient) {
        dao.delete(patient);
    }

    public List<Patient> tousLesPatients() {
        return dao.getAll();
    }

    public Patient trouverParId(int id) {
        return dao.findById(id);
    }

    public List<Patient> rechercherParNom(String nom) {
        return dao.findByNom(nom);
    }
}
