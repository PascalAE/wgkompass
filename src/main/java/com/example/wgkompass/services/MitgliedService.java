package com.example.wgkompass.services;

import com.example.wgkompass.models.Mitglied;
import com.example.wgkompass.repositories.MitgliedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * The MitgliedService class provides business logic and operations for Mitglied (member) entities.
 * It interfaces with the MitgliedRepository for data persistence and retrieval.
 */
@Service
public class MitgliedService {

    @Autowired
    private MitgliedRepository mitgliedRepository;

    /**
     * Retrieves a Mitglied entity by its ID.
     *
     * @param id The ID of the Mitglied to be retrieved.
     * @return An Optional containing the Mitglied if found, or an empty Optional otherwise.
     */
    public Optional<Mitglied> getById(Long id) {
        return mitgliedRepository.findById(id);
    }

    /**
     * Retrieves all Mitglied entities.
     *
     * @return An Iterable of all Mitglied entities.
     */
    public Iterable<Mitglied> getAll() {
        return mitgliedRepository.findAll();
    }

    /**
     * Retrieves all Mitglied entities associated with a specific WG ID.
     *
     * @param wgId The ID of the WG.
     * @return A List of Mitglied entities associated with the WG.
     */
    public List<Mitglied> getAllByWgId(Long wgId) {
        return mitgliedRepository.findAllByWgId(wgId);
    }

    /**
     * Saves a Mitglied entity to the repository.
     * If the Mitglied already exists, it will be updated; otherwise, a new Mitglied will be created.
     *
     * @param mitglied The Mitglied entity to be saved.
     * @return The saved Mitglied entity.
     */
    public Mitglied save(Mitglied mitglied) {
        return mitgliedRepository.save(mitglied);
    }

}
