package com.monapp.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "commande")
public class Commande {

    public enum Statut { en_attente, acceptee, refusee, terminee }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_commande")
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_restaurant", referencedColumnName = "id", nullable = false)
    private Restaurant restaurant;

    @Column(name = "date_commande")
    private LocalDateTime dateCommande; // laisser null à la création → DEFAULT CURRENT_TIMESTAMP côté DB

    @Enumerated(EnumType.STRING)
    @Column(name = "statut", nullable = false, length = 20)
    private Statut statut = Statut.en_attente;

    // Getters / Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Restaurant getRestaurant() { return restaurant; }
    public void setRestaurant(Restaurant restaurant) { this.restaurant = restaurant; }

    public LocalDateTime getDateCommande() { return dateCommande; }
    public void setDateCommande(LocalDateTime dateCommande) { this.dateCommande = dateCommande; }

    public Statut getStatut() { return statut; }
    public void setStatut(Statut statut) { this.statut = statut; }
}
