package com.gl.gestionclinique.Repository;

import com.gl.gestionclinique.Model.Consultation;
import com.gl.gestionclinique.Model.Patient;
import java.util.List;

public interface IConsultationDAO extends GenericDAO<Consultation> {
    Consultation findById(int id);
    List<Consultation> findByPatient(Patient patient);
}
