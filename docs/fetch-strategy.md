# JPA relationships and fetch strategy

The associations are mapped lazily. `@ManyToOne` and `@OneToOne` are eager by default in JPA, so they declare `FetchType.LAZY` explicitly. Collections are also explicitly lazy. Load related records only for the use case that needs them.

| Association | Owning side and mapping | Fetch | Cascade | Reason |
| --- | --- | --- | --- | --- |
| Agency - Vehicle | `Vehicule.agence` owns the many-to-one foreign key; `Agence.vehicules` is the inverse collection. | LAZY | None | Vehicles remain independent if an agency is removed. |
| Agency - Employee | `Employe.agence` owns the many-to-one foreign key; `Agence.employes` is the inverse collection. | LAZY | None | Employee records should not be deleted with an agency. |
| Vehicle - Equipment | `Vehicule.equipements` owns the join table `vehicule_equipement`; `Equipement.vehicules` is inverse. | LAZY | None | Equipment is shared, so removing a vehicle must not remove equipment. |
| Client - Reservation | `Reservation.client` owns the many-to-one foreign key; `Client.reservations` is inverse. | LAZY | PERSIST from client to reservations | Saving a client may save new reservations without cascading updates or deletes. |
| Reservation - Vehicle | `Reservation.vehicule` owns the many-to-one foreign key; `Vehicule.reservations` is inverse. | LAZY | None | Reservations reference a vehicle but do not own its lifecycle. |
| Reservation - Contract | `Contrat.reservation` owns the unique one-to-one foreign key; `Reservation.contrat` is inverse. | LAZY | ALL from reservation to contract | A contract belongs to its reservation and follows that reservation's lifecycle. |
| Contract - Payment | `Paiement.contrat` owns the many-to-one foreign key; `Contrat.paiements` is inverse. | LAZY | ALL + orphan removal from contract to payments | Payments depend on their contract and are removed with it or when detached from it. |
| Vehicle - Maintenance | `Maintenance.vehicule` owns the many-to-one foreign key; `Vehicule.maintenances` is inverse. | LAZY | PERSIST from vehicle to maintenance | Saving a vehicle can save new maintenance records; removing a vehicle does not erase their history. |

## Keeping both sides in sync

Use the aggregate helper methods when changing a bidirectional association: `agence.addVehicule(vehicule)`, `agence.addEmploye(employe)`, `client.addReservation(reservation)`, `vehicule.addEquipement(equipement)`, `vehicule.addReservation(reservation)`, and `vehicule.addMaintenance(maintenance)`. For payments, call `contrat.addPaiement(paiement)` instead of only adding to the list or setting the foreign-key side. These helpers update both object references. Use `reservation.attachContrat(contrat)` to keep the one-to-one references aligned.

`Vehicule.equipements` is a `Set` because equipment can be shared across vehicles and should not be duplicated in one vehicle's association. No `@Data` annotation is used on entities; generated `equals`, `hashCode`, and `toString` methods can recurse through bidirectional relationships.

## Schema checks

After starting the application with MySQL available, check that:

- `employe`, `vehicule`, `reservation`, `contrat`, `paiement`, and `maintenance` have their expected foreign keys.
- `contrat.reservation_id` is unique, so each reservation has at most one contract.
- `vehicule_equipement` is the only join table for the vehicle-equipment many-to-many relation.
- No cascade delete is configured from agency to vehicles, agency to employees, reservation to vehicle, or vehicle to equipment.

The lab's `ddl-auto=update` setting is for local exercises. Use versioned database migrations for a production deployment.
