package com.example.wgkompass.controllers;

import com.example.wgkompass.dto.MitgliedDto;
import com.example.wgkompass.exception.InvalidRequestException;
import com.example.wgkompass.models.WG;
import com.example.wgkompass.services.WGService;
import com.example.wgkompass.utils.ValidationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import com.example.wgkompass.services.MitgliedService;
import com.example.wgkompass.models.Mitglied;

import java.util.List;
import java.util.Optional;

/**
 * The MitgliedController class handles HTTP requests related to Mitglied (member) entities.
 * It provides methods to create, retrieve, update, and list members, using the MitgliedService for business logic.
 */
@RestController
@RequestMapping("/mitglied")
public class MitgliedController {

    @Autowired
    private MitgliedService mitgliedService;

    @Autowired
    private WGService wgService;

    /**
     * Retrieves all Mitglied (member) entries and returns them as a list of MitgliedDto.
     *
     * @return ResponseEntity containing a list of MitgliedDto.
     */
    @GetMapping("/all")
    public ResponseEntity<List<MitgliedDto>> getAll() {
        List<Mitglied> mitglieder = (List<Mitglied>) mitgliedService.getAll();
        List<MitgliedDto> mitgliedDtos = mitglieder.stream().map(this::convertToMitgliedDto).toList();
        return ResponseEntity.ok(mitgliedDtos);
    }

    /**
     * Retrieves a Mitglied (member) entry by its ID and returns it as MitgliedDto.
     *
     * @param id The ID of the Mitglied.
     * @return ResponseEntity containing MitgliedDto if found, or a not found response.
     */
    @GetMapping("/{id}")
    public ResponseEntity<MitgliedDto> getById(@PathVariable Long id) {
        Optional<Mitglied> mitglied = mitgliedService.getById(id);
        if (mitglied.isPresent()) {
            MitgliedDto mitgliedDto = convertToMitgliedDto(mitglied.get());
            return ResponseEntity.ok(mitgliedDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Retrieves all Mitglied (member) entities by WG ID.
     *
     * @param wgId the ID of the WG
     * @return ResponseEntity containing a list of MitgliedDto objects
     */
    @GetMapping("/wg/{wgId}")
    public ResponseEntity<List<MitgliedDto>> getMitgliederByWGId(@PathVariable Long wgId) {
        List<Mitglied> mitglieder = mitgliedService.getAllByWgId(wgId);
        List<MitgliedDto> mitgliedDtos = mitglieder.stream().map(this::convertToMitgliedDto).toList();
        return ResponseEntity.ok(mitgliedDtos);
    }


    /**
     * Creates a new Mitglied (member) entry from the provided MitgliedDto.
     *
     * @param mitgliedDto Data transfer object for Mitglied.
     * @return ResponseEntity containing the created MitgliedDto.
     */
    @PostMapping("/create")
    public ResponseEntity<MitgliedDto> create(@RequestBody MitgliedDto mitgliedDto) {
        if (ValidationUtils.containsIllegalCharacters(mitgliedDto.getVorname()) || ValidationUtils.containsIllegalCharacters(mitgliedDto.getNachname())) {
            throw new InvalidRequestException("Illegal characters in name");
        }
        Mitglied mitglied = convertToMitglied(mitgliedDto);
        Mitglied savedMitglied = mitgliedService.save(mitglied);
        MitgliedDto savedMitgliedDto = convertToMitgliedDto(savedMitglied);
        return ResponseEntity.ok(savedMitgliedDto);
    }

    /**
     * Updates an existing Mitglied (member) entry with the provided ID and MitgliedDto.
     *
     * @param id The ID of the Mitglied to update.
     * @param mitgliedDto Data transfer object for Mitglied.
     * @return ResponseEntity containing the updated MitgliedDto, or a not found response.
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<MitgliedDto> update(@PathVariable Long id, @RequestBody MitgliedDto mitgliedDto) {
        Optional<Mitglied> existingMitglied = mitgliedService.getById(id);
        if (existingMitglied.isPresent()) {
            if (ValidationUtils.containsIllegalCharacters(mitgliedDto.getVorname()) || ValidationUtils.containsIllegalCharacters(mitgliedDto.getNachname())) {
                throw new InvalidRequestException("Illegal characters in name");
            }
            Mitglied updatedMitglied = convertToMitglied(mitgliedDto);
            updatedMitglied.setId(id); // make sure the correct id is used
            Mitglied savedMitglied = mitgliedService.save(updatedMitglied);
            MitgliedDto savedMitgliedDto = convertToMitgliedDto(savedMitglied);
            return ResponseEntity.ok(savedMitgliedDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Utility method descriptions

    /**
     * Converts a Mitglied (member) entity to a MitgliedDto.
     *
     * @param mitglied Mitglied entity.
     * @return MitgliedDto corresponding to the Mitglied entity.
     */
    private MitgliedDto convertToMitgliedDto(Mitglied mitglied) {
        return new MitgliedDto(
                mitglied.getId(),
                mitglied.getVorname(),
                mitglied.getNachname(),
                mitglied.getWg().getId());
    }

    /**
     * Converts a MitgliedDto to a Mitglied (member) entity.
     *
     * @param mitgliedDto MitgliedDto.
     * @return Mitglied entity corresponding to the MitgliedDto.
     */
    private Mitglied convertToMitglied(MitgliedDto mitgliedDto) {
        Mitglied mitglied = new Mitglied();
        mitglied.setId(mitgliedDto.getId());
        mitglied.setVorname(mitgliedDto.getVorname());
        mitglied.setNachname(mitgliedDto.getNachname());

        if (mitgliedDto.getWgId() != null) {
            WG wg = wgService.getById(mitgliedDto.getWgId()).orElse(null);
            mitglied.setWg(wg);
        }

        return mitglied;
    }
}
