package com.gl.gestionclinique.Repository;

import com.gl.gestionclinique.Model.Medecin;
import java.util.List;

public interface IMedecinDAO extends GenericDAO<Medecin> {
    Medecin findById(int id);
    List<Medecin> findAll();
}
