package com.gl.gestionclinique.Model;

import lombok.Data;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Table(name = "patients")
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nom;
    private String prenom;
    private LocalDate dateNaissance;
    private String sexe;
    private String telephone;
    private String adresse;

    @Enumerated(EnumType.STRING)
    private GroupeSanguin groupeSanguin;

    @Column(columnDefinition = "TEXT")
    private String antecedentsMedicaux;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<RendezVous> rendezVous;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Consultation> consultations;

    @Override
    public String toString() {
        return nom + " " + prenom;
    }
}
