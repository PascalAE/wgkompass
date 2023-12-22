package com.example.wgkompass.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * The Aufgabe class represents a task or duty within a shared living community (WG, Wohngemeinschaft).
 * It includes details about the task, such as its title and description, and its association with a specific WG
 * and the responsible member.
 */
@Getter
@Setter
@Entity
@Table(name = "Aufgabe")
public class Aufgabe {

    /**
     * The unique ID of the task.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The title of the task.
     */
    @Column(name = "Titel")
    private String titel;

    /**
     * The description of the task.
     */
    @Column(name = "Beschreibung")
    private String beschreibung;

    /**
     * The WG to which the task belongs.
     * This is represented as a many-to-one relationship, where many tasks can be associated with one WG.
     */
    @ManyToOne
    @JoinColumn(name = "wg_id", nullable = false)
    private WG wg;

    /**
     * The member responsible for this task.
     * This is also a many-to-one relationship, indicating that a member can be responsible for many tasks.
     */
    @ManyToOne
    @JoinColumn(name = "verantwortliches_mitglied_id", nullable = true)
    private Mitglied verantwortlichesMitglied;
}