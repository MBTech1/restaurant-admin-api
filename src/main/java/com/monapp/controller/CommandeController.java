package com.monapp.controller;

import com.monapp.model.Commande;
import com.monapp.model.Commande.Statut;
import com.monapp.repository.CommandeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/commandes")
public class CommandeController {

    @Autowired
    private CommandeRepository commandeRepository;

    // GET /api/commandes → toutes les commandes
    @GetMapping
    public List<Commande> getAll() {
        return commandeRepository.findAll();
    }

    // GET /api/commandes/{id} → par ID
    @GetMapping("/{id}")
    public Commande getById(@PathVariable Long id) {
        return commandeRepository.findById(id).orElse(null);
    }

    // GET /api/commandes/by-restaurant/{restaurantId}
    //   filtres optionnels: ?statut=acceptee&from=2025-08-10T00:00&to=2025-08-12T23:59
    @GetMapping("/by-restaurant/{restaurantId}")
    public List<Commande> getByRestaurant(@PathVariable Long restaurantId,
                                          @RequestParam(required = false) Statut statut,
                                          @RequestParam(required = false) String from,
                                          @RequestParam(required = false) String to) {
        if (statut != null) {
            return commandeRepository.findByRestaurantIdAndStatut(restaurantId, statut);
        }
        if (from != null && to != null) {
            return commandeRepository.findByDateCommandeBetween(
                    LocalDateTime.parse(from), LocalDateTime.parse(to)
            );
        }
        return commandeRepository.findByRestaurantId(restaurantId);
    }

    // POST /api/commandes → création (laisser dateCommande null pour DEFAULT CURRENT_TIMESTAMP DB)
    @PostMapping
    public Commande create(@RequestBody Commande cmd) {
        return commandeRepository.save(cmd);
    }

    // PUT /api/commandes/{id} → mise à jour complète
    @PutMapping("/{id}")
    public Commande update(@PathVariable Long id, @RequestBody Commande updated) {
        return commandeRepository.findById(id)
                .map(c -> {
                    c.setRestaurant(updated.getRestaurant());
                    c.setDateCommande(updated.getDateCommande());
                    c.setStatut(updated.getStatut());
                    return commandeRepository.save(c);
                })
                .orElse(null);
    }

    // PATCH /api/commandes/{id}/statut → changer uniquement le statut
    @PatchMapping("/{id}/statut")
    public Commande updateStatut(@PathVariable Long id, @RequestParam Statut statut) {
        return commandeRepository.findById(id)
                .map(c -> {
                    c.setStatut(statut);
                    return commandeRepository.save(c);
                })
                .orElse(null);
    }

    // DELETE /api/commandes/{id}
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        commandeRepository.deleteById(id);
    }
}
