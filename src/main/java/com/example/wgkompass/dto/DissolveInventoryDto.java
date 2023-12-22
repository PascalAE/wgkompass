package com.example.wgkompass.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Data Transfer Object for dissolving WG inventory. This class is used to transfer data
 * about which WG member wishes to take over which inventory items, in the process of WG dissolution.
 */
@Getter
@Setter
@AllArgsConstructor
public class DissolveInventoryDto {
    /**
     * The unique identifier of the WG whose inventory is being dissolved.
     */
    private Long wgId;
    /**
     * A list of mappings between inventory items and WG members.
     */
    private List<InventoryMemberMapping> inventoryMappings;

    /**
     * Inner class representing a mapping between an inventory item and a WG member.
     */
    @Getter
    @Setter
    @AllArgsConstructor
    public static class InventoryMemberMapping {
        /**
         * The unique identifier of the inventory item.
         */
        private Long inventarId;

        /**
         * The unique identifier of the WG member taking over the inventory item.
         */
        private Long mitgliedId;
    }
}
