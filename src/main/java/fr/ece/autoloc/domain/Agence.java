package fr.ece.autoloc.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Agence {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAgence;

    private String nom;
    private String ville;
    private String adresse;
    private String telephone;

    @OneToMany(mappedBy = "agence", fetch = FetchType.LAZY)
    private List<Vehicule> vehicules = new ArrayList<>();

    @OneToMany(mappedBy = "agence", fetch = FetchType.LAZY)
    private List<Employe> employes = new ArrayList<>();

    public void addVehicule(Vehicule vehicule) {
        if (vehicule != null && !vehicules.contains(vehicule)) {
            vehicules.add(vehicule);
            vehicule.setAgence(this);
        }
    }

    public void removeVehicule(Vehicule vehicule) {
        if (vehicules.remove(vehicule) && vehicule.getAgence() == this) {
            vehicule.setAgence(null);
        }
    }

    public void addEmploye(Employe employe) {
        if (employe != null && !employes.contains(employe)) {
            employes.add(employe);
            employe.setAgence(this);
        }
    }

    public void removeEmploye(Employe employe) {
        if (employes.remove(employe) && employe.getAgence() == this) {
            employe.setAgence(null);
        }
    }
}
