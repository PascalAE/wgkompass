package com.example.wgkompass.services;

import com.example.wgkompass.models.Aufgabe;
import com.example.wgkompass.repositories.AufgabeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * The AufgabeService class provides business logic and operations for Aufgabe (task) entities.
 * It interfaces with the AufgabeRepository for data persistence and retrieval.
 */
@Service
public class AufgabeService {
    @Autowired
    private AufgabeRepository aufgabeRepository;

    /**
     * Retrieves an Aufgabe entity by its ID.
     *
     * @param id The ID of the Aufgabe to be retrieved.
     * @return An Optional containing the Aufgabe if found, or an empty Optional otherwise.
     */
    public Optional<Aufgabe> getById(Long id) {
        return aufgabeRepository.findById(id);
    }

    /**
     * Retrieves all Aufgabe entities.
     *
     * @return An Iterable of all Aufgabe entities.
     */
    public Iterable<Aufgabe> getAll() {
        return aufgabeRepository.findAll();
    }

    /**
     * Retrieves all Aufgabe entities associated with a specific WG ID.
     *
     * @param wgId The ID of the WG.
     * @return A List of Aufgabe entities associated with the WG.
     */
    public List<Aufgabe> getAllByWgId(Long wgId) {
        return aufgabeRepository.findAllByWgId(wgId);
    }

    /**
     * Saves an Aufgabe entity to the repository.
     * If the Aufgabe already exists, it will be updated; otherwise, a new Aufgabe will be created.
     *
     * @param aufgabe The Aufgabe entity to be saved.
     * @return The saved Aufgabe entity.
     */
    public Aufgabe save(Aufgabe aufgabe) {
        return aufgabeRepository.save(aufgabe);
    }
}