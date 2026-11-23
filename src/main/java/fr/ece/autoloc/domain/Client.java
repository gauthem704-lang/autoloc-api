package fr.ece.autoloc.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idClient;

    private String nom;
    private String prenom;
    private String email;
    private String telephone;
    private String numPermis;
    private LocalDate dateInscription;

    @OneToMany(mappedBy = "client", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private List<Reservation> reservations = new ArrayList<>();

    public void addReservation(Reservation reservation) {
        if (reservation != null && !reservations.contains(reservation)) {
            reservations.add(reservation);
            reservation.setClient(this);
        }
    }

    public void removeReservation(Reservation reservation) {
        if (reservations.remove(reservation) && reservation.getClient() == this) {
            reservation.setClient(null);
        }
    }
}
