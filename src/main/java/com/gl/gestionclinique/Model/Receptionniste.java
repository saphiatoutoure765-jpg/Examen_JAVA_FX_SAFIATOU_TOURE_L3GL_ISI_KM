package com.gl.gestionclinique.Model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.persistence.*;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "receptionnistes")
public class Receptionniste extends Utilisateur {
    private String bureau;
}
