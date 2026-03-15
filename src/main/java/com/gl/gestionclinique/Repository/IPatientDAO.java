package com.gl.gestionclinique.Repository;

import com.gl.gestionclinique.Model.Patient;
import java.util.List;

public interface IPatientDAO extends GenericDAO<Patient> {
    Patient findById(int id);
    List<Patient> findByNom(String nom);
}
