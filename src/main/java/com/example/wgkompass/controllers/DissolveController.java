package com.example.wgkompass.controllers;

import com.example.wgkompass.models.Inventar;
import com.example.wgkompass.models.Mitglied;
import com.example.wgkompass.services.InventarService;
import com.example.wgkompass.services.MitgliedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.wgkompass.dto.DissolveInventoryDto;
import com.example.wgkompass.dto.DissolveResultDto;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * The DissolveController class handles HTTP requests related to the dissolution process of WG inventories.
 * It facilitates the allocation of inventory items among WG members and calculates
 * corresponding financial obligations based on current inventory value.
 */
@RestController
@RequestMapping("/dissolve")
public class DissolveController {
    @Autowired
    private InventarService inventarService;

    @Autowired
    private MitgliedService mitgliedService;


    /**
     * Handles a POST request to dissolve WG inventory by calculating the current value of each inventory item
     * and determining the financial obligations of WG members based on the allocation of items.
     *
     * @param dissolveInventoryDto DTO containing the WG ID and a list of inventory to member mappings.
     * @return A ResponseEntity containing a DissolveResultDto, which includes financial obligations and inventory values.
     */
    @PostMapping("/inventory")
    public ResponseEntity<DissolveResultDto> dissolveInventory(@RequestBody DissolveInventoryDto dissolveInventoryDto) {
        DissolveResultDto result = calculateDissolveResult(dissolveInventoryDto, dissolveInventoryDto.getWgId());
        return ResponseEntity.ok(result);
    }

    /**
     * Calculates the financial obligations and current values of inventory items based on the provided mapping.
     *
     * @param dto The DTO containing the mappings of inventory items to WG members.
     * @param wgId The ID of the WG to which the inventory belongs.
     * @return A DissolveResultDto containing lists of financial obligations and inventory values.
     */
    private DissolveResultDto calculateDissolveResult(DissolveInventoryDto dto, Long wgId) {
        List<DissolveResultDto.MemberFinancialObligation> obligations = new ArrayList<>();
        List<DissolveResultDto.InventoryValue> inventoryValues = new ArrayList<>();

        List<Mitglied> wgMembers = mitgliedService.getAllByWgId(wgId);
        Map<String, Double> aggregatedDebts = new HashMap<>();

        for (DissolveInventoryDto.InventoryMemberMapping mapping : dto.getInventoryMappings()) {
            Inventar inventar = inventarService.getById(mapping.getInventarId()).orElse(null);
            if (inventar != null) {
                double currentValue = calculateCurrentValue(inventar, LocalDate.now());
                inventoryValues.add(new DissolveResultDto.InventoryValue(inventar.getId(), inventar.getPreis(), currentValue));

                double amountPerMember = currentValue / wgMembers.size();
                for (Mitglied recipient : wgMembers) {
                    if (!recipient.getId().equals(mapping.getMitgliedId())) {
                        String key = mapping.getMitgliedId() + "-" + recipient.getId();
                        aggregatedDebts.put(key, aggregatedDebts.getOrDefault(key, 0.0) + amountPerMember);
                    }
                }
            }
        }

        for (Map.Entry<String, Double> entry : aggregatedDebts.entrySet()) {
            String[] ids = entry.getKey().split("-");
            Long payerId = Long.parseLong(ids[0]);
            Long recipientId = Long.parseLong(ids[1]);
            obligations.add(new DissolveResultDto.MemberFinancialObligation(payerId, recipientId, entry.getValue()));
        }

        return new DissolveResultDto(obligations, inventoryValues);
    }

    /**
     * Calculates the current value of an inventory item based on its purchase price, depreciation rate, and years since purchase.
     *
     * @param inventar The inventory item for which the current value is being calculated.
     * @param currentDate The date to which the current value is calculated.
     * @return The current value of the inventory item.
     */
    private double calculateCurrentValue(Inventar inventar, LocalDate currentDate) {
        LocalDate kaufdatum = inventar.getKaufdatum().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
        long yearsSincePurchase = ChronoUnit.DAYS.between(kaufdatum, currentDate) / 365;
        return calculateDepreciation(inventar.getPreis(), inventar.getAbschreibungssatz(), (int) yearsSincePurchase);
    }

    /**
     * Recursively calculates the depreciated value of an item over a specified number of years.
     *
     * @param startValue The original value of the item.
     * @param depreciationRate The annual depreciation rate of the item in percentage.
     * @param years The number of years over which to calculate the depreciation.
     * @return The depreciated value of the item.
     */
    private double calculateDepreciation(double startValue, double depreciationRate, int years) {
        if (years <= 0) {
            return startValue;
        } else {
            double newValue = startValue * (1 - depreciationRate / 100);
            return calculateDepreciation(newValue, depreciationRate, years - 1);
        }
    }
}
