package com.monapp.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table (name = "reservation")

public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String client;
    private LocalDateTime dateHeure;

    @ManyToOne
    private Restaurant restaurant;

    // Getters et setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getClient() { return client; }
    public void setClient(String client) { this.client = client; }

    public LocalDateTime getDateHeure() { return dateHeure; }
    public void setDateHeure(LocalDateTime dateHeure) { this.dateHeure = dateHeure; }

    public Restaurant getRestaurant() { return restaurant; }
    public void setRestaurant(Restaurant restaurant) { this.restaurant = restaurant; }
}
