package com.example.wgkompass.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Data Transfer Object for Mitglied (member). This class is used to transfer data related to members
 * of a shared living community (WG) between different layers of the application, especially for RESTful responses.
 * It encapsulates the data attributes of a member.
 */
@Getter
@Setter
@AllArgsConstructor
public class MitgliedDto {
    /**
     * The unique identifier for the member.
     */
    private Long id;

    /**
     * The first name of the member.
     */
    private String vorname;

    /**
     * The last name of the member.
     */
    private String nachname;

    /**
     * The identifier of the WG (shared living community) to which this member belongs.
     */
    private Long wgId;
}
