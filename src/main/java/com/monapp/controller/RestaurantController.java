package com.monapp.controller;

import com.monapp.model.Restaurant;
import com.monapp.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/restaurants")
public class RestaurantController {

    @Autowired
    private RestaurantRepository restaurantRepository;

    // GET /api/restaurants → liste de tous les restaurants
    @GetMapping
    public List<Restaurant> getAllRestaurants() {
        return restaurantRepository.findAll();
    }

    // GET /api/restaurants/{id} → restaurant par ID
    @GetMapping("/{id}")
    public Restaurant getRestaurantById(@PathVariable Long id) {
        return restaurantRepository.findById(id).orElse(null);
    }

    // GET /api/restaurants/by-nom/{nom} → recherche exacte par nom
    @GetMapping("/by-nom/{nom}")
    public Restaurant getRestaurantByNom(@PathVariable String nom) {
        return restaurantRepository.findByNom(nom);
    }

    // GET /api/restaurants/search → recherche par nom ou adresse (partiel)
    @GetMapping("/search")
    public List<Restaurant> searchRestaurants(@RequestParam(required = false) String nom,
                                              @RequestParam(required = false) String adresse) {
        if (nom != null) {
            return restaurantRepository.findByNomContainingIgnoreCase(nom);
        }
        if (adresse != null) {
            return restaurantRepository.findByAdresseContainingIgnoreCase(adresse);
        }
        return restaurantRepository.findAll();
    }

    // POST /api/restaurants → création d'un restaurant
    @PostMapping
    public Restaurant createRestaurant(@RequestBody Restaurant restaurant) {
        // Les champs telephone, typeCuisine, emailContact sont pris en charge automatiquement
        return restaurantRepository.save(restaurant);
    }

    // PUT /api/restaurants/{id} → mise à jour complète
    @PutMapping("/{id}")
    public Restaurant updateRestaurant(@PathVariable Long id, @RequestBody Restaurant updatedRestaurant) {
        return restaurantRepository.findById(id)
                .map(restaurant -> {
                    restaurant.setNom(updatedRestaurant.getNom());
                    restaurant.setAdresse(updatedRestaurant.getAdresse());
                    restaurant.setTelephone(updatedRestaurant.getTelephone());
                    restaurant.setTypeCuisine(updatedRestaurant.getTypeCuisine());
                    restaurant.setEmailContact(updatedRestaurant.getEmailContact());
                    return restaurantRepository.save(restaurant);
                })
                .orElse(null);
    }

    // PATCH /api/restaurants/{id} → mise à jour partielle
    @PatchMapping("/{id}")
    public Restaurant patchRestaurant(@PathVariable Long id, @RequestBody Restaurant patch) {
        return restaurantRepository.findById(id)
                .map(restaurant -> {
                    if (patch.getNom() != null) restaurant.setNom(patch.getNom());
                    if (patch.getAdresse() != null) restaurant.setAdresse(patch.getAdresse());
                    if (patch.getTelephone() != null) restaurant.setTelephone(patch.getTelephone());
                    if (patch.getTypeCuisine() != null) restaurant.setTypeCuisine(patch.getTypeCuisine());
                    if (patch.getEmailContact() != null) restaurant.setEmailContact(patch.getEmailContact());
                    return restaurantRepository.save(restaurant);
                })
                .orElse(null);
    }

    // DELETE /api/restaurants/{id} → suppression
    @DeleteMapping("/{id}")
    public void deleteRestaurant(@PathVariable Long id) {
        restaurantRepository.deleteById(id);
    }
}
