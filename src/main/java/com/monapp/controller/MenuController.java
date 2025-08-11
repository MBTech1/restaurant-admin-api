package com.monapp.controller;

import com.monapp.model.Menu;
import com.monapp.model.Menu.TypePlat;
import com.monapp.repository.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/menus")
public class MenuController {

    @Autowired
    private MenuRepository menuRepository;

    // GET /api/menus → tous les plats
    @GetMapping
    public List<Menu> getAllMenus() {
        return menuRepository.findAll();
    }

    // GET /api/menus/{id} → plat par ID
    @GetMapping("/{id}")
    public Menu getMenuById(@PathVariable Long id) {
        return menuRepository.findById(id).orElse(null);
    }

    // GET /api/menus/by-restaurant/{restaurantId} → plats d’un restaurant
    @GetMapping("/by-restaurant/{restaurantId}")
    public List<Menu> getByRestaurant(@PathVariable Long restaurantId,
                                      @RequestParam(required = false) Boolean disponible,
                                      @RequestParam(required = false) TypePlat typePlat) {
        if (Boolean.TRUE.equals(disponible) && typePlat != null) {
            return menuRepository.findByRestaurantIdAndTypePlat(restaurantId, typePlat)
                    .stream().filter(Menu::getDisponible).toList();
        }
        if (Boolean.TRUE.equals(disponible)) {
            return menuRepository.findByRestaurantIdAndDisponibleTrue(restaurantId);
        }
        if (typePlat != null) {
            return menuRepository.findByRestaurantIdAndTypePlat(restaurantId, typePlat);
        }
        return menuRepository.findByRestaurantId(restaurantId);
    }

    // GET /api/menus/search?nom=...
    @GetMapping("/search")
    public List<Menu> searchByNom(@RequestParam String nom) {
        return menuRepository.findByNomContainingIgnoreCase(nom);
    }

    // POST /api/menus → création
    @PostMapping
    public Menu createMenu(@RequestBody Menu menu) {
        return menuRepository.save(menu);
    }

    // PUT /api/menus/{id} → mise à jour complète
    @PutMapping("/{id}")
    public Menu updateMenu(@PathVariable Long id, @RequestBody Menu updated) {
        return menuRepository.findById(id)
                .map(m -> {
                    m.setRestaurant(updated.getRestaurant());
                    m.setNom(updated.getNom());
                    m.setDescription(updated.getDescription());
                    m.setImage(updated.getImage());
                    m.setPrix(updated.getPrix());
                    m.setTypePlat(updated.getTypePlat());
                    m.setDisponible(updated.getDisponible());
                    m.setDateAjout(updated.getDateAjout());
                    return menuRepository.save(m);
                })
                .orElse(null);
    }

    // PATCH /api/menus/{id} → mise à jour partielle
    @PatchMapping("/{id}")
    public Menu patchMenu(@PathVariable Long id, @RequestBody Menu patch) {
        return menuRepository.findById(id)
                .map(m -> {
                    if (patch.getRestaurant() != null) m.setRestaurant(patch.getRestaurant());
                    if (patch.getNom() != null) m.setNom(patch.getNom());
                    if (patch.getDescription() != null) m.setDescription(patch.getDescription());
                    if (patch.getImage() != null) m.setImage(patch.getImage());
                    if (patch.getPrix() != null) m.setPrix(patch.getPrix());
                    if (patch.getTypePlat() != null) m.setTypePlat(patch.getTypePlat());
                    if (patch.getDisponible() != null) m.setDisponible(patch.getDisponible());
                    if (patch.getDateAjout() != null) m.setDateAjout(patch.getDateAjout());
                    return menuRepository.save(m);
                })
                .orElse(null);
    }

    // DELETE /api/menus/{id} → suppression
    @DeleteMapping("/{id}")
    public void deleteMenu(@PathVariable Long id) {
        menuRepository.deleteById(id);
    }
}
