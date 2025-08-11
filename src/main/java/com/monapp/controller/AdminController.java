package com.monapp.controller;

import com.monapp.model.Admin;
import com.monapp.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admins")
public class AdminController {

    @Autowired
    private AdminRepository adminRepository;

    // GET /api/admins → liste de tous les admins
    @GetMapping
    public List<Admin> getAllAdmins() {
        return adminRepository.findAll();
    }

    // GET /api/admins/{id} → admin par ID
    @GetMapping("/{id}")
    public Admin getAdminById(@PathVariable Long id) {
        return adminRepository.findById(id).orElse(null);
    }

    // POST /api/admins → création d'un nouvel admin
    @PostMapping
    public Admin createAdmin(@RequestBody Admin admin) {
        return adminRepository.save(admin);
    }

    // PUT /api/admins/{id} → mise à jour complète d'un admin
    @PutMapping("/{id}")
    public Admin updateAdmin(@PathVariable Long id, @RequestBody Admin updatedAdmin) {
        return adminRepository.findById(id)
                .map(admin -> {
                    admin.setNom(updatedAdmin.getNom());
                    admin.setEmail(updatedAdmin.getEmail());
                    admin.setMotDePasse(updatedAdmin.getMotDePasse());
                    return adminRepository.save(admin);
                })
                .orElse(null);
    }

    // DELETE /api/admins/{id} → suppression d'un admin
    @DeleteMapping("/{id}")
    public void deleteAdmin(@PathVariable Long id) {
        adminRepository.deleteById(id);
    }
}
