package com.monapp.model;

import jakarta.persistence.*;

@Entity
@Table(name = "commande_item")
public class CommandeItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_commande_item")
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_commande", referencedColumnName = "id_commande", nullable = false)
    private Commande commande;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_menu", referencedColumnName = "id_menu", nullable = false)
    private Menu menu;

    @Column(nullable = false)
    private int quantite = 1;

    // Getters / Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Commande getCommande() { return commande; }
    public void setCommande(Commande commande) { this.commande = commande; }

    public Menu getMenu() { return menu; }
    public void setMenu(Menu menu) { this.menu = menu; }

    public int getQuantite() { return quantite; }
    public void setQuantite(int quantite) { this.quantite = quantite; }
}
