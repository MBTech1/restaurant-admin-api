package com.monapp.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "menu")
public class Menu {

    public enum TypePlat { entree, plat, dessert, boisson }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_menu")
    private Long id;

    // FK → restaurants.id  (voir note en bas)
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_restaurant", referencedColumnName = "id", nullable = false)
    private Restaurant restaurant;

    @Column(name = "nom", nullable = false, length = 100)
    private String nom;

    @Column(name = "description")
    private String description;

    @Column(name = "image", length = 255)
    private String image;

    @Column(name = "prix", nullable = false, precision = 10, scale = 2)
    private BigDecimal prix;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_plat", nullable = false, length = 20)
    private TypePlat typePlat;

    @Column(name = "disponible")
    private Boolean disponible = true;

    @Column(name = "date_ajout")
    private LocalDateTime dateAjout; // mappé sur DEFAULT CURRENT_TIMESTAMP côté DB

    // Getters / Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Restaurant getRestaurant() { return restaurant; }
    public void setRestaurant(Restaurant restaurant) { this.restaurant = restaurant; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }

    public BigDecimal getPrix() { return prix; }
    public void setPrix(BigDecimal prix) { this.prix = prix; }

    public TypePlat getTypePlat() { return typePlat; }
    public void setTypePlat(TypePlat typePlat) { this.typePlat = typePlat; }

    public Boolean getDisponible() { return disponible; }
    public void setDisponible(Boolean disponible) { this.disponible = disponible; }

    public LocalDateTime getDateAjout() { return dateAjout; }
    public void setDateAjout(LocalDateTime dateAjout) { this.dateAjout = dateAjout; }
}
