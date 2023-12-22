package com.example.wgkompass.controllers;

import com.example.wgkompass.dto.WGDto;
import com.example.wgkompass.exception.InvalidRequestException;
import com.example.wgkompass.utils.ValidationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import com.example.wgkompass.services.WGService;
import com.example.wgkompass.models.WG;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * The WGController class handles HTTP requests related to Wohngemeinschaft (WG) entities.
 * It provides methods to create, retrieve, update, and list WGs, using the WGService for business logic.
 */
@RestController
@RequestMapping("/wg")
public class WGController {

    @Autowired
    private WGService wgService;

    /**
     * Retrieves all WGs and returns them as a list of WGDto.
     *
     * @return ResponseEntity containing a list of WGDto.
     */
    @GetMapping("/all")
    public ResponseEntity<List<WGDto>> getAll() {
        List<WG> wgs = (List<WG>) wgService.getAll();
        List<WGDto> wgDtos = wgs.stream().map(this::convertToWGDto).collect(Collectors.toList());
        return ResponseEntity.ok(wgDtos);
    }

    /**
     * Retrieves a WG by its ID and returns it as WGDto.
     *
     * @param id The ID of the WG.
     * @return ResponseEntity containing WGDto if found, or a not found response.
     */
    @GetMapping("/{id}")
    public ResponseEntity<WGDto> getById(@PathVariable Long id) {
        Optional<WG> wg = wgService.getById(id);
        if (wg.isPresent()) {
            WGDto wgDto = convertToWGDto(wg.get());
            return ResponseEntity.ok(wgDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Creates a new WG from the provided WGDto.
     *
     * @param wgDto Data transfer object for WG.
     * @return ResponseEntity containing the created WGDto.
     */
    @PostMapping("/create")
    public ResponseEntity<WGDto> create(@RequestBody WGDto wgDto) {
        if (ValidationUtils.containsIllegalCharacters(wgDto.getName())) {
            throw new InvalidRequestException("Illegal characters in name");
        }
        WG wg = convertToWG(wgDto);
        WG savedWG = wgService.save(wg);
        WGDto savedWGDto = convertToWGDto(savedWG);
        return ResponseEntity.ok(savedWGDto);
    }

    /**
     * Updates an existing WG with the provided ID and WGDto.
     *
     * @param id The ID of the WG to update.
     * @param wgDto Data transfer object for WG.
     * @return ResponseEntity containing the updated WGDto, or a not found response.
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<WGDto> update(@PathVariable Long id, @RequestBody WGDto wgDto) {
        Optional<WG> existingWG = wgService.getById(id);
        if (existingWG.isPresent()) {
            if (ValidationUtils.containsIllegalCharacters(wgDto.getName())) {
                throw new InvalidRequestException("Illegal characters in name");
            }
            WG updatedWG = convertToWG(wgDto);
            updatedWG.setId(id); // make sure the right wg is updated
            WG savedWG = wgService.save(updatedWG);
            WGDto savedWGDto = convertToWGDto(savedWG);
            return ResponseEntity.ok(savedWGDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Utility method descriptions

    /**
     * Converts a WG entity to a WGDto.
     *
     * @param wg WG entity.
     * @return WGDto corresponding to the WG entity.
     */
    private WGDto convertToWGDto(WG wg) {
        return new WGDto(wg.getId(), wg.getName());
    }

    /**
     * Converts a WGDto to a WG entity.
     *
     * @param dto WGDto.
     * @return WG entity corresponding to the WGDto.
     */
    private WG convertToWG(WGDto dto) {
        WG wg = new WG();
        wg.setId(dto.getId());
        wg.setName(dto.getName());
        return wg;
    }
}