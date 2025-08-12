package com.monapp.controller;

import com.monapp.model.Utilisateur;
import com.monapp.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;


@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestParam String nom,
                                           @RequestParam String email,
                                           @RequestParam String motDePasse) {
        if (utilisateurRepository.findByEmail(email) != null) {
            return ResponseEntity.status(409).body("Email déjà utilisé");
        }
        Utilisateur u = new Utilisateur();
        u.setNom(nom);
        u.setEmail(email);
        u.setMotDePasse(motDePasse);
        utilisateurRepository.save(u);
        return ResponseEntity.status(201).body("Inscription réussie");
    }

    @PostMapping("/login")
    public String login(@RequestParam String email, @RequestParam String motDePasse) {
        Utilisateur user = utilisateurRepository.findByEmail(email);
        if (user != null && user.getMotDePasse().equals(motDePasse)) {
            return "Connexion réussie";
        } else {
            return "Échec de la connexion";
        }
    }

    @PostMapping("/logout")
    public String logout() { return "Déconnexion réussie"; }
}
