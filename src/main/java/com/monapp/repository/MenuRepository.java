package com.monapp.repository;

import com.monapp.model.Menu;
import com.monapp.model.Menu.TypePlat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, Long> {

    // Tous les plats d’un restaurant
    List<Menu> findByRestaurantId(Long restaurantId);

    // Filtrages utiles
    List<Menu> findByRestaurantIdAndDisponibleTrue(Long restaurantId);
    List<Menu> findByRestaurantIdAndTypePlat(Long restaurantId, TypePlat typePlat);
    List<Menu> findByNomContainingIgnoreCase(String nom);
}
