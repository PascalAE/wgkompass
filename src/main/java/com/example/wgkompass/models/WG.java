package com.example.wgkompass.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name= "WG")
/**
 * The WG class represents a shared living community (WG, Wohngemeinschaft).

 * It contains properties to uniquely identify a WG and to manage its relationships with inventory, members, and tasks.
 */
public class WG {
    /**
     * The unique ID of the wg.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The name of the wg.
     */
    private String name;

    /**
     * The list of inventory items belonging to the wg.
     * Each inventory item is connected to this WG via 'mappedBy'.
     */
    @OneToMany(mappedBy = "wg")
    private List<Inventar> inventare;

    /**
     * The list of members belonging to this wg.
     * Each member is connected to this WG via 'mappedBy'.
     */
    @OneToMany(mappedBy = "wg")
    private List<Mitglied> mitglieder;

    /**
     * The list of tasks that exists within this wg.
     * Each task is connected to this WG via 'mappedBy'.
     */
    @OneToMany(mappedBy = "wg")
    private List<Aufgabe> aufgaben;
}
