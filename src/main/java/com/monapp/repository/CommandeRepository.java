package com.monapp.repository;

import com.monapp.model.Commande;
import com.monapp.model.Commande.Statut;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface CommandeRepository extends JpaRepository<Commande, Long> {

    List<Commande> findByRestaurantId(Long restaurantId);

    List<Commande> findByStatut(Statut statut);

    List<Commande> findByRestaurantIdAndStatut(Long restaurantId, Statut statut);

    List<Commande> findByDateCommandeBetween(LocalDateTime from, LocalDateTime to);
}
