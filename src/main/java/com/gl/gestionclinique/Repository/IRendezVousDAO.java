package com.gl.gestionclinique.Repository;

import com.gl.gestionclinique.Model.Medecin;
import com.gl.gestionclinique.Model.RendezVous;
import java.time.LocalDateTime;
import java.util.List;

public interface IRendezVousDAO extends GenericDAO<RendezVous> {
    RendezVous findById(int id);
    List<RendezVous> findByMedecin(Medecin medecin);
    List<RendezVous> findByDate(LocalDateTime date);
    boolean existsConflict(Medecin medecin, LocalDateTime dateHeure);
}
