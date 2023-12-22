package com.example.wgkompass.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Data Transfer Object for WG (Wohngemeinschaft, shared living community). This class is used to
 * transfer data related to shared living communities between different layers of the application,
 * particularly for RESTful responses. It encapsulates the data attributes of a WG.
 */
@Getter
@Setter
@AllArgsConstructor
public class WGDto {
    /**
     * The unique identifier for the shared living community.
     */
    private Long id;

    /**
     * The name of the shared living community.
     */
    private String name;
}
