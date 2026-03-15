package com.gl.gestionclinique.Model;

import lombok.Data;
import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "rendez_vous")
public class RendezVous {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private LocalDateTime dateHeure;

    @Enumerated(EnumType.STRING)
    private StatutRendezVous statut;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "medecin_id")
    private Medecin medecin;

    @OneToOne(mappedBy = "rendezVous", cascade = CascadeType.ALL)
    private Consultation consultation;

    @Override
    public String toString() {
        return dateHeure + " - " + (patient != null ? patient.getNom() : "");
    }
}
