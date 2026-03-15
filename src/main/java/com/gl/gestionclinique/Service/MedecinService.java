package com.gl.gestionclinique.Service;

import com.gl.gestionclinique.Model.Medecin;
import com.gl.gestionclinique.Repository.IMedecinDAO;
import java.util.List;

public class MedecinService {

    private final IMedecinDAO dao;

    public MedecinService(IMedecinDAO dao) {
        this.dao = dao;
    }

    public void ajouter(Medecin medecin) {
        dao.save(medecin);
    }

    public void modifier(Medecin medecin) {
        dao.update(medecin);
    }

    public void supprimer(Medecin medecin) {
        dao.delete(medecin);
    }

    public List<Medecin> tous() {
        return dao.getAll();
    }

    public Medecin trouverParId(int id) {
        return dao.findById(id);
    }
}
