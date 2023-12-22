package com.example.wgkompass.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Data Transfer Object for Inventar. This class is used to transfer data related to inventory items
 * between different layers of the application, particularly for RESTful responses.
 * It encapsulates the data attributes of an inventory item.
 */
@Getter
@Setter
@AllArgsConstructor
public class InventarDto {
    /**
     * The unique identifier for the inventory item.
     */
    private Long id;

    /**
     * The name of the inventory item.
     */
    private String name;

    /**
     * The price of the inventory item.
     */
    private Double preis;

    /**
     * The purchase date of the inventory item.
     */
    private Date kaufdatum;

    /**
     * The depreciation rate of the inventory item.
     */
    private Double abschreibungssatz;

    /**
     * The identifier of the WG (shared living community) to which this inventory item belongs.
     */
    private Long wgId;
}
