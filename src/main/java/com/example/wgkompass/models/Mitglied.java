package com.example.wgkompass.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "Mitglied")
public class Mitglied {
    /**
     * The unique ID of the member.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * The first name of the member.
     */
    @Column(name = "Vorname")
    private String vorname;

    /**
     * The last name of the member.
     */
    @Column(name = "Nachname")
    private String nachname;

    /**
     * The WG to which the member belongs.
     * This is represented as a many-to-one relationship, where many members can belong to one WG.
     */
    @ManyToOne
    @JoinColumn(name = "wg_id", nullable = false)
    private WG wg;

}
