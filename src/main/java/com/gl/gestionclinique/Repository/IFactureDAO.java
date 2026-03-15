package com.gl.gestionclinique.Repository;

import com.gl.gestionclinique.Model.Facture;
import java.util.List;

public interface IFactureDAO extends GenericDAO<Facture> {
    Facture findById(int id);
    List<Facture> findAll();
}
