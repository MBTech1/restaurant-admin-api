package com.monapp.controller;

import com.monapp.model.Utilisateur;
import com.monapp.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

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
    public String logout() {
        return "Déconnexion réussie";
    }
}
