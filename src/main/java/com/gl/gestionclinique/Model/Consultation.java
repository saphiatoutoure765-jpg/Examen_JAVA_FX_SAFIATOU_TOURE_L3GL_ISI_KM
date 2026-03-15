package com.gl.gestionclinique.Model;

import lombok.Data;
import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "consultations")
public class Consultation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private LocalDate date;

    @Column(columnDefinition = "TEXT")
    private String diagnostic;

    @Column(columnDefinition = "TEXT")
    private String observations;

    @Column(columnDefinition = "TEXT")
    private String prescription;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "medecin_id")
    private Medecin medecin;

    @OneToOne
    @JoinColumn(name = "rendez_vous_id")
    private RendezVous rendezVous;

    @OneToOne(mappedBy = "consultation", cascade = CascadeType.ALL)
    private Facture facture;

    @Override
    public String toString() {
        String nomPatient = patient != null ? patient.getNom() + " " + patient.getPrenom() : "?";
        String nomMedecin = medecin != null ? "Dr. " + medecin.getNom() : "?";
        return nomPatient + " - " + nomMedecin + " - " + date;
    }
}