package com.monapp.controller;

import com.monapp.model.Reservation;
import com.monapp.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    @Autowired
    private ReservationRepository reservationRepository;

    // GET /api/reservations → liste de toutes les réservations
    @GetMapping
    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    // GET /api/reservations/{id} → réservation par ID
    @GetMapping("/{id}")
    public Reservation getReservationById(@PathVariable Long id) {
        return reservationRepository.findById(id).orElse(null);
    }

    // GET /api/reservations/by-restaurant/{restaurantId} → réservations d'un restaurant
    @GetMapping("/by-restaurant/{restaurantId}")
    public List<Reservation> getByRestaurant(@PathVariable Long restaurantId) {
        return reservationRepository.findByRestaurantId(restaurantId);
    }

    // GET /api/reservations/by-date → réservations pour une date précise
    @GetMapping("/by-date")
    public List<Reservation> getByDate(@RequestParam String dateHeure) {
        return reservationRepository.findByDateHeure(LocalDateTime.parse(dateHeure));
    }

    // GET /api/reservations/by-client/{client} → réservations d'un client
    @GetMapping("/by-client/{client}")
    public List<Reservation> getByClient(@PathVariable String client) {
        return reservationRepository.findByClient(client);
    }

    // POST /api/reservations → création d'une réservation
    @PostMapping
    public Reservation createReservation(@RequestBody Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    // PUT /api/reservations/{id} → mise à jour complète d'une réservation
    @PutMapping("/{id}")
    public Reservation updateReservation(@PathVariable Long id, @RequestBody Reservation updatedReservation) {
        return reservationRepository.findById(id)
                .map(reservation -> {
                    reservation.setClient(updatedReservation.getClient());
                    reservation.setDateHeure(updatedReservation.getDateHeure());
                    reservation.setRestaurant(updatedReservation.getRestaurant());
                    return reservationRepository.save(reservation);
                })
                .orElse(null);
    }

    // DELETE /api/reservations/{id} → suppression d'une réservation
    @DeleteMapping("/{id}")
    public void deleteReservation(@PathVariable Long id) {
        reservationRepository.deleteById(id);
    }
}
