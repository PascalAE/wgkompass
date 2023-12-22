package com.example.wgkompass.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Data Transfer Object representing the result of dissolving WG inventory.
 * It contains financial obligations generated from the inventory dissolution process,
 * and the current values of the inventory items.
 */
@Getter
@Setter
@AllArgsConstructor
public class DissolveResultDto {
    /**
     * A list of financial obligations between WG members as a result of inventory distribution.
     */
    private List<MemberFinancialObligation> obligations;

    /**
     * A list of current values of inventory items after dissolution.
     */
    private List<InventoryValue> inventoryValues;

    /**
     * Inner class representing a financial obligation of one member to another.
     */
    @Getter
    @Setter
    @AllArgsConstructor
    public static class MemberFinancialObligation {
        /**
         * The unique identifier of the member who owes money.
         */
        private Long payerId;

        /**
         * The unique identifier of the member to whom money is owed.
         */
        private Long recipientId;

        /**
         * The amount of money owed.
         */
        private double amount;
    }

    /**
     * Inner class representing the value of an inventory item both originally and currently.
     */
    @Getter
    @Setter
    @AllArgsConstructor
    public static class InventoryValue {
        /**
         * The unique identifier of the inventory item.
         */
        private Long inventarId;

        /**
         * The original purchase price of the inventory item.
         */
        private double originalPrice;

        /**
         * The current value of the inventory item after depreciation.
         */
        private double currentValue;
    }
}
