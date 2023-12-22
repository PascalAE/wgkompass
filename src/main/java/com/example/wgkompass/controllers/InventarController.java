package com.example.wgkompass.controllers;

import com.example.wgkompass.dto.InventarDto;
import com.example.wgkompass.exception.InvalidRequestException;
import com.example.wgkompass.models.WG;
import com.example.wgkompass.services.WGService;
import com.example.wgkompass.utils.ValidationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import com.example.wgkompass.services.InventarService;
import com.example.wgkompass.models.Inventar;

import java.util.List;
import java.util.Optional;

/**
 * The InventarController class handles HTTP requests related to Inventar (inventory) entities.
 * It provides methods to create, retrieve, update, and list inventory items, using the InventarService for business logic.
 */
@RestController
@RequestMapping("/inventar")
public class InventarController {

    @Autowired
    private InventarService inventarService;

    @Autowired
    private WGService wgService;

    /**
     * Retrieves all Inventar (inventory) entries and returns them as a list of InventarDto.
     *
     * @return ResponseEntity containing a list of InventarDto.
     */
    @GetMapping("/all")
    public ResponseEntity<List<InventarDto>> getAll() {
        List<Inventar> inventar = (List<Inventar>) inventarService.getAll();
        List<InventarDto> inventarDtos = inventar.stream().map(this::convertToInventarDto).toList();
        return ResponseEntity.ok(inventarDtos);
    }

    /**
     * Retrieves an Inventar (inventory) entry by its ID and returns it as InventarDto.
     *
     * @param id The ID of the Inventar.
     * @return ResponseEntity containing InventarDto if found, or a not found response.
     */
    @GetMapping("/{id}")
    public ResponseEntity<InventarDto> getById(@PathVariable Long id) {
        Optional<Inventar> inventar = inventarService.getById(id);
        if (inventar.isPresent()) {
            InventarDto inventarDto = convertToInventarDto(inventar.get());
            return ResponseEntity.ok(inventarDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Retrieves all Inventar entries by WG ID.
     * @param wgId the ID of the WG
     * @return ResponseEntity containing a list of InventarDto objects
     */
    @GetMapping("/wg/{wgId}")
    public ResponseEntity<List<InventarDto>> getInventarByWGId(@PathVariable Long wgId) {
        List<Inventar> inventarListe = inventarService.getAllByWgId(wgId);
        List<InventarDto> inventarDtos = inventarListe.stream().map(this::convertToInventarDto).toList();
        return ResponseEntity.ok(inventarDtos);
    }

    /**
     * Creates a new Inventar (inventory) entry from the provided InventarDto.
     *
     * @param inventarDto Data transfer object for Inventar.
     * @return ResponseEntity containing the created InventarDto.
     */
    @PostMapping("/create")
    public ResponseEntity<InventarDto> create(@RequestBody InventarDto inventarDto) {
        if (ValidationUtils.containsIllegalCharacters(inventarDto.getName())) {
            throw new InvalidRequestException("Illegal characters in name");
        }
        Inventar inventar = convertToInventar(inventarDto);
        Inventar savedInventar = inventarService.save(inventar);
        InventarDto savedInventarDto = convertToInventarDto(savedInventar);
        return ResponseEntity.ok(savedInventarDto);
    }

    /**
     * Updates an existing Inventar (inventory) entry with the provided ID and InventarDto.
     *
     * @param id The ID of the Inventar to update.
     * @param inventarDto Data transfer object for Inventar.
     * @return ResponseEntity containing the updated InventarDto, or a not found response.
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<InventarDto> update(@PathVariable Long id, @RequestBody InventarDto inventarDto) {
        Optional<Inventar> existingInventar = inventarService.getById(id);
        if (existingInventar.isPresent()) {
            if (ValidationUtils.containsIllegalCharacters(inventarDto.getName())) {
                throw new InvalidRequestException("Illegal characters in name");
            }
            Inventar updatedInventar = convertToInventar(inventarDto);
            updatedInventar.setId(id); // make sure the right inventar is updated
            Inventar savedInventar = inventarService.save(updatedInventar);
            InventarDto savedInventarDto = convertToInventarDto(savedInventar);
            return ResponseEntity.ok(savedInventarDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Converts an Inventar entity to an InventarDto object.
     * @param inventar The Inventar entity to be converted.
     * @return An InventarDto object containing the data from the Inventar entity.
     */
    private InventarDto convertToInventarDto(Inventar inventar) {
        return new InventarDto(
                inventar.getId(),
                inventar.getName(),
                inventar.getPreis(),
                inventar.getKaufdatum(),
                inventar.getAbschreibungssatz(),
                inventar.getWg().getId()
        );
    }

    /**
     * Converts an InventarDto object to an Inventar entity.
     * @param inventarDto The InventarDto object to be converted.
     * @return An Inventar entity containing the data from the InventarDto.
     */
    private Inventar convertToInventar(InventarDto inventarDto) {
        Inventar inventar = new Inventar();
        inventar.setId(inventarDto.getId());
        inventar.setName(inventarDto.getName());
        inventar.setPreis(inventarDto.getPreis());
        inventar.setKaufdatum(inventarDto.getKaufdatum());
        inventar.setAbschreibungssatz(inventarDto.getAbschreibungssatz());

        if(inventarDto.getWgId() != null) {
            WG wg = wgService.getById(inventarDto.getWgId()).orElse(null);
            inventar.setWg(wg);
        }

        return inventar;
    }
}