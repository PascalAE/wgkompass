package com.example.wgkompass.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * The Inventar class represents an inventory item within a shared living community (WG, Wohngemeinschaft).
 * It includes details about the item, such as its name, price, purchase date, and depreciation rate,
 * along with its association with a specific WG.
 */
@Getter
@Setter
@Entity
@Table(name = "Inventar")
public class Inventar {

    /**
     * The unique ID of the inventory item.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The name of the inventory item.
     */
    @Column(name = "Name")
    private String name;

    /**
     * The price of the inventory item.
     */
    @Column(name = "Preis")
    private Double preis;

    /**
     * The purchase date of the inventory item.
     */
    @Column(name = "Kaufdatum")
    private Date kaufdatum;

    /**
     * The depreciation rate of the inventory item.
     */
    @Column(name = "Abschreibungssatz")
    private Double abschreibungssatz;

    /**
     * The WG to which the inventory item belongs.
     * This is represented as a many-to-one relationship, where many inventory items can belong to one WG.
     */
    @ManyToOne
    @JoinColumn(name = "wg_id", nullable = false)
    private WG wg;
}