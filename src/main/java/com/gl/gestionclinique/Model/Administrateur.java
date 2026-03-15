package com.gl.gestionclinique.Model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.persistence.*;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "administrateurs")
public class Administrateur extends Utilisateur {
    private String niveau;
}
