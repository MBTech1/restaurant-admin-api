package com.monapp.controller;

import com.monapp.model.CommandeItem;
import com.monapp.repository.CommandeItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/commande-items")
public class CommandeItemController {

    @Autowired
    private CommandeItemRepository commandeItemRepository;

    // GET /api/commande-items → tous les items
    @GetMapping
    public List<CommandeItem> getAll() {
        return commandeItemRepository.findAll();
    }

    // GET /api/commande-items/{id} → par ID
    @GetMapping("/{id}")
    public CommandeItem getById(@PathVariable Long id) {
        return commandeItemRepository.findById(id).orElse(null);
    }

    // GET /api/commande-items/by-commande/{commandeId}
    @GetMapping("/by-commande/{commandeId}")
    public List<CommandeItem> getByCommande(@PathVariable Long commandeId) {
        return commandeItemRepository.findByCommandeId(commandeId);
    }

    // GET /api/commande-items/by-menu/{menuId}
    @GetMapping("/by-menu/{menuId}")
    public List<CommandeItem> getByMenu(@PathVariable Long menuId) {
        return commandeItemRepository.findByMenuId(menuId);
    }

    // POST /api/commande-items → créer un item
    @PostMapping
    public CommandeItem create(@RequestBody CommandeItem item) {
        return commandeItemRepository.save(item);
    }

    // PUT /api/commande-items/{id} → mise à jour complète
    @PutMapping("/{id}")
    public CommandeItem update(@PathVariable Long id, @RequestBody CommandeItem updated) {
        return commandeItemRepository.findById(id)
                .map(ci -> {
                    ci.setCommande(updated.getCommande());
                    ci.setMenu(updated.getMenu());
                    ci.setQuantite(updated.getQuantite());
                    return commandeItemRepository.save(ci);
                })
                .orElse(null);
    }

    // DELETE /api/commande-items/{id}
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        commandeItemRepository.deleteById(id);
    }
}
