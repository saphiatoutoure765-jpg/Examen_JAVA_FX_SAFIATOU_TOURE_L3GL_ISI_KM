package com.gl.gestionclinique.Service;

import com.gl.gestionclinique.Model.Facture;
import com.gl.gestionclinique.Model.StatutFacture;
import com.gl.gestionclinique.Repository.IFactureDAO;
import java.time.LocalDate;
import java.util.List;

public class FactureService {

    private final IFactureDAO dao;

    public FactureService(IFactureDAO dao) {
        this.dao = dao;
    }

    public void generer(Facture facture) {
        facture.setDate(LocalDate.now());
        facture.setStatut(StatutFacture.NON_PAYE);
        dao.save(facture);
    }

    public void marquerPayee(Facture facture) {
        facture.setStatut(StatutFacture.PAYE);
        dao.update(facture);
    }

    public void modifier(Facture facture) {
        dao.update(facture);
    }

    public List<Facture> historique() {
        return dao.getAll();
    }

    public Facture trouverParId(int id) {
        return dao.findById(id);
    }
}
