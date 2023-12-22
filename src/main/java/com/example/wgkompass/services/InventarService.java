package com.example.wgkompass.services;

import com.example.wgkompass.models.Inventar;
import com.example.wgkompass.repositories.InventarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * The InventarService class provides business logic and operations for Inventar (inventory) entities.
 * It uses the InventarRepository for data persistence and retrieval.
 */
@Service
public class InventarService {

    @Autowired
    private InventarRepository inventarRepository;

    /**
     * Retrieves an Inventar entity by its ID.
     *
     * @param id The ID of the Inventar to be retrieved.
     * @return An Optional containing the Inventar if found, or an empty Optional otherwise.
     */
    public Optional<Inventar> getById(Long id) {
        return inventarRepository.findById(id);
    }

    /**
     * Retrieves all Inventar entities.
     *
     * @return An Iterable of all Inventar entities.
     */
    public Iterable<Inventar> getAll() {
        return inventarRepository.findAll();
    }

    /**
     * Retrieves all Inventar entities associated with a specific WG ID.
     *
     * @param wgId The ID of the WG.
     * @return A List of Inventar entities associated with the WG.
     */
    public List<Inventar> getAllByWgId(Long wgId) {
        return inventarRepository.findAllByWgId(wgId);
    }

    /**
     * Saves an Inventar entity to the repository.
     * If the Inventar already exists, it will be updated; otherwise, a new Inventar will be created.
     *
     * @param inventar The Inventar entity to be saved.
     * @return The saved Inventar entity.
     */
    public Inventar save(Inventar inventar) {
        return inventarRepository.save(inventar);
    }

}