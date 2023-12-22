package com.example.wgkompass.controllers;

import com.example.wgkompass.dto.AufgabeDto;
import com.example.wgkompass.exception.InvalidRequestException;
import com.example.wgkompass.models.Mitglied;
import com.example.wgkompass.models.WG;
import com.example.wgkompass.services.MitgliedService;
import com.example.wgkompass.services.WGService;
import com.example.wgkompass.utils.ValidationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import com.example.wgkompass.services.AufgabeService;
import com.example.wgkompass.models.Aufgabe;

import java.util.List;
import java.util.Optional;

/**
 * The AufgabeController class handles HTTP requests related to Aufgabe (task) entities.
 * It provides methods to create, retrieve, update, and list tasks, using the AufgabeService for business logic.
 */
@RestController
@RequestMapping("/aufgabe")
public class AufgabeController {

    @Autowired
    private AufgabeService aufgabeService;

    @Autowired
    private WGService wgService;

    @Autowired
    private MitgliedService mitgliedService;

    /**
     * Retrieves all Aufgabe (task) entries and returns them as a list of AufgabeDto.
     *
     * @return ResponseEntity containing a list of AufgabeDto.
     */
    @GetMapping("/all")
    public ResponseEntity<List<AufgabeDto>> getAll() {
        List<Aufgabe> aufgaben = (List<Aufgabe>) aufgabeService.getAll();
        List<AufgabeDto> aufgabeDtos = aufgaben.stream().map(this::convertToAufgabeDto).toList();
        return ResponseEntity.ok(aufgabeDtos);
    }

    /**
     * Retrieves an Aufgabe (task) entry by its ID and returns it as AufgabeDto.
     *
     * @param id The ID of the WG.
     * @return ResponseEntity containing AufgabeDto if found, or a not found response.
     */
    @GetMapping("/{id}")
    public ResponseEntity<AufgabeDto> getById(@PathVariable Long id) {
        Optional<Aufgabe> aufgabe = aufgabeService.getById(id);
        if (aufgabe.isPresent()) {
            AufgabeDto aufgabeDto = convertToAufgabeDto(aufgabe.get());
            return ResponseEntity.ok(aufgabeDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Retrieves a list of Aufgabe (tasks) entry by wg id  and returns it as AufgabeDto list.
     *
     * @param wgId The ID of the WG.
     * @return ResponseEntity containing List of AufgabeDto.
     */
    @GetMapping("/wg/{wgId}")
    public ResponseEntity<List<AufgabeDto>> getAufgabenByWGId(@PathVariable Long wgId) {
        List<Aufgabe> aufgaben = (List<Aufgabe>) aufgabeService.getAllByWgId(wgId);
        List<AufgabeDto> aufgabeDtos = aufgaben.stream().map(this::convertToAufgabeDto).toList();
        return ResponseEntity.ok(aufgabeDtos);
    }

    /**
     * Creates a new Aufgabe (task) entry from the provided AufgabeDto.
     *
     * @param aufgabeDto Data transfer object for Aufgabe.
     * @return ResponseEntity containing the created AufgabeDto.
     */
    @PostMapping("/create")
    public ResponseEntity<AufgabeDto> create(@RequestBody AufgabeDto aufgabeDto) {
        if (ValidationUtils.containsIllegalCharacters(aufgabeDto.getTitel())) {
            throw new InvalidRequestException("Illegal characters in name");
        }
        Aufgabe aufgabe = convertToAufgabe(aufgabeDto);
        Aufgabe savedAufgabe = aufgabeService.save(aufgabe);
        AufgabeDto savedAufgabeDto = convertToAufgabeDto(savedAufgabe);
        return ResponseEntity.ok(savedAufgabeDto);
    }

    /**
     * Updates an existing Aufgabe (task) entry with the provided ID and AufgabeDto.
     *
     * @param id The ID of the Aufgabe to update.
     * @param aufgabeDto Data transfer object for Aufgabe.
     * @return ResponseEntity containing the updated AufgabeDto, or a not found response.
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<AufgabeDto> update(@PathVariable Long id, @RequestBody AufgabeDto aufgabeDto) {
        Optional<Aufgabe> existingAufgabe = aufgabeService.getById(id);
        if (existingAufgabe.isPresent()) {
            if (ValidationUtils.containsIllegalCharacters(aufgabeDto.getTitel())) {
                throw new InvalidRequestException("Illegal characters in name");
            }
            Aufgabe updatedAufgabe = convertToAufgabe(aufgabeDto);
            updatedAufgabe.setId(id); // make sure the right aufgabe is updated
            Aufgabe savedAufgabe = aufgabeService.save(updatedAufgabe);
            AufgabeDto savedAufgabeDto = convertToAufgabeDto(savedAufgabe);
            return ResponseEntity.ok(savedAufgabeDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Utility method descriptions

    /**
     * Converts an Aufgabe (task) entity to an AufgabeDto.
     *
     * @param aufgabe Aufgabe entity.
     * @return AufgabeDto corresponding to the Aufgabe entity.
     */
    private AufgabeDto convertToAufgabeDto(Aufgabe aufgabe) {
        Long verantwortlichesMitgliedId = null;
        if (aufgabe.getVerantwortlichesMitglied() != null) {
            verantwortlichesMitgliedId = aufgabe.getVerantwortlichesMitglied().getId();
        }

        return new AufgabeDto(
                aufgabe.getId(),
                aufgabe.getTitel(),
                aufgabe.getBeschreibung(),
                aufgabe.getWg().getId(),
                verantwortlichesMitgliedId
        );
    }

    /**
     * Converts an AufgabeDto to an Aufgabe (task) entity.
     *
     * @param aufgabeDto AufgabeDto.
     * @return Aufgabe entity corresponding to the AufgabeDto.
     */
    private Aufgabe convertToAufgabe(AufgabeDto aufgabeDto) {
        Aufgabe aufgabe = new Aufgabe();
        aufgabe.setId(aufgabeDto.getId());
        aufgabe.setTitel(aufgabeDto.getTitel());
        aufgabe.setBeschreibung(aufgabeDto.getBeschreibung());

        if (aufgabeDto.getWgId() != null) {
            WG wg = wgService.getById(aufgabeDto.getWgId()).orElse(null);
            aufgabe.setWg(wg);
        }

        if (aufgabeDto.getVerantwortlichesMitgliedId() != null) {
            Mitglied verantwortlichesMitglied = mitgliedService.getById(aufgabeDto.getVerantwortlichesMitgliedId()).orElse(null);
            aufgabe.setVerantwortlichesMitglied(verantwortlichesMitglied);
        } else {
            aufgabe.setVerantwortlichesMitglied(null);
        }

        return aufgabe;
    }
}
