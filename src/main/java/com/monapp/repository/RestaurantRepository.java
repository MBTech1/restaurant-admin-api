package com.monapp.repository;

import com.monapp.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    // Trouver par nom exact
    Restaurant findByNom(String nom);

    // Trouver par nom partiel (contient)
    List<Restaurant> findByNomContainingIgnoreCase(String nom);

    // Trouver par adresse partielle
    List<Restaurant> findByAdresseContainingIgnoreCase(String adresse);

    // Trouver par téléphone exact
    Restaurant findByTelephone(String telephone);

    // Trouver par téléphone partiel
    List<Restaurant> findByTelephoneContainingIgnoreCase(String telephone);

    // Trouver par type de cuisine (partiel)
    List<Restaurant> findByTypeCuisineContainingIgnoreCase(String typeCuisine);

    // Trouver par email de contact exact
    Restaurant findByEmailContact(String emailContact);

    // Trouver par email de contact partiel
    List<Restaurant> findByEmailContactContainingIgnoreCase(String emailContact);
}
