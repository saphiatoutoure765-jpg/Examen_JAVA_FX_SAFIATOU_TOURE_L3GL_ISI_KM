package com.gl.gestionclinique.Service;

import com.gl.gestionclinique.Model.Medecin;
import com.gl.gestionclinique.Model.RendezVous;
import com.gl.gestionclinique.Model.StatutRendezVous;
import com.gl.gestionclinique.Repository.IRendezVousDAO;
import java.time.LocalDateTime;
import java.util.List;

public class RendezVousService {

    private final IRendezVousDAO dao;

    public RendezVousService(IRendezVousDAO dao) {
        this.dao = dao;
    }

    public void planifier(RendezVous rv) {
        if (dao.existsConflict(rv.getMedecin(), rv.getDateHeure())) {
            throw new RuntimeException("Ce médecin a déjà un rendez-vous à cet horaire.");
        }
        rv.setStatut(StatutRendezVous.PROGRAMME);
        dao.save(rv);
    }

    public void modifier(RendezVous rv) {
        dao.update(rv);
    }

    public void annuler(RendezVous rv) {
        rv.setStatut(StatutRendezVous.ANNULE);
        dao.update(rv);
    }

    public List<RendezVous> tousLesRendezVous() {
        return dao.getAll();
    }

    public List<RendezVous> rendezVousDuJour() {
        return dao.findByDate(LocalDateTime.now());
    }

    public List<RendezVous> parMedecin(Medecin medecin) {
        return dao.findByMedecin(medecin);
    }
}
