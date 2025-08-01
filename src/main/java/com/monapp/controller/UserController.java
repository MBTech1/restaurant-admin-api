package com.monapp.controller;

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

    @GetMapping
    public List<Utilisateur> getAllUsers() {
        return utilisateurRepository.findAll();
    }

    @PostMapping
    public Utilisateur addUser(@RequestBody Utilisateur user) {
        return utilisateurRepository.save(user);
    }

    @PutMapping("/{id}")
    public Utilisateur updateUser(@PathVariable Long id, @RequestBody Utilisateur userData) {
        Utilisateur user = utilisateurRepository.findById(id).orElse(null);
        if (user != null) {
            user.setNom(userData.getNom());
            user.setEmail(userData.getEmail());
            user.setMotDePasse(userData.getMotDePasse());
            return utilisateurRepository.save(user);
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        utilisateurRepository.deleteById(id);
    }
}
