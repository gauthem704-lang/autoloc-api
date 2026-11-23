package fr.ece.autoloc.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Equipement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEquipement;

    private String libelle;

    @ManyToMany(mappedBy = "equipements", fetch = FetchType.LAZY)
    private Set<Vehicule> vehicules = new HashSet<>();

    public void addVehicule(Vehicule vehicule) {
        if (vehicule != null) {
            vehicule.addEquipement(this);
        }
    }

    public void removeVehicule(Vehicule vehicule) {
        if (vehicule != null) {
            vehicule.removeEquipement(this);
        }
    }
}
