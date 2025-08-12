package com.monapp.controller;

import org.springframework.http.ResponseEntity;
import com.monapp.model.Utilisateur;
import com.monapp.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    // GET /users -> liste complète
    @GetMapping
    public List<Utilisateur> getAllUsers() {
        return utilisateurRepository.findAll();
    }

    // GET /users/{id} -> récupérer un user par ID
    @GetMapping("/{id}")
    public ResponseEntity<Utilisateur> getUserById(@PathVariable Long id) {
        return utilisateurRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET /users/by-email/{email} -> récupérer l'ID via l'email
    @GetMapping("/by-email/{email}")
    public ResponseEntity<Long> getUserIdByEmail(@PathVariable String email) {
        Utilisateur user = utilisateurRepository.findByEmail(email);
        if (user != null) {
            return ResponseEntity.ok(user.getId()); // On ne retourne que l'ID
        }
        return ResponseEntity.notFound().build();
    }

    // POST /users -> création (role optionnel)
    @PostMapping
    public ResponseEntity<Utilisateur> addUser(@RequestBody Utilisateur user) {
        // (optionnel) éviter doublons d'email
        Utilisateur existing = utilisateurRepository.findByEmail(user.getEmail());
        if (existing != null) {
            return ResponseEntity.status(409).build(); // Conflict
        }
        // user.getRole() peut être null -> restera NULL en base
        Utilisateur saved = utilisateurRepository.save(user);
        return ResponseEntity.status(201).body(saved);
    }

    // PUT /users/{id} -> mise à jour complète (inclut role optionnel)
    @PutMapping("/{id}")
    public ResponseEntity<Utilisateur> updateUser(@PathVariable Long id, @RequestBody Utilisateur userData) {
        return utilisateurRepository.findById(id)
                .map(user -> {
                    user.setNom(userData.getNom());
                    user.setEmail(userData.getEmail());
                    user.setMotDePasse(userData.getMotDePasse());
                    user.setRole(userData.getRole()); // <= peut être null, c’est voulu
                    Utilisateur saved = utilisateurRepository.save(user);
                    return ResponseEntity.ok(saved);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // PATCH /users/{id}/role?role=ADMIN -> mise à jour partielle du rôle (optionnel)
    @PatchMapping("/{id}/role")
    public ResponseEntity<Utilisateur> patchUserRole(@PathVariable Long id,
                                                     @RequestParam(required = false) String role) {
        return utilisateurRepository.findById(id)
                .map(user -> {
                    user.setRole(role); // peut être null -> efface le rôle
                    Utilisateur saved = utilisateurRepository.save(user);
                    return ResponseEntity.ok(saved);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE /users/{id} -> suppression
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        if (utilisateurRepository.existsById(id)) {
            utilisateurRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
