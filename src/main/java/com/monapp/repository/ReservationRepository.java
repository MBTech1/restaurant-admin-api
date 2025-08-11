package com.monapp.repository;

import com.monapp.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    // Trouver toutes les réservations pour un restaurant donné
    List<Reservation> findByRestaurantId(Long restaurantId);

    // Trouver toutes les réservations pour une date précise
    List<Reservation> findByDateHeure(LocalDateTime dateHeure);

    // Trouver toutes les réservations d'un client donné
    List<Reservation> findByClient(String client);
}
