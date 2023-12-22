package com.example.wgkompass.services;

import com.example.wgkompass.models.WG;
import com.example.wgkompass.repositories.WGRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * The WGService class provides business logic and operations for WG (Wohngemeinschaft) entities.
 * It utilizes the WGRepository for data persistence and retrieval.
 */
@Service
public class WGService {

    @Autowired
    private WGRepository wgRepository;

    /**
     * Retrieves a WG entity by its ID.
     *
     * @param id The ID of the WG to be retrieved.
     * @return An Optional containing the WG if found, or an empty Optional otherwise.
     */
    public Optional<WG> getById(Long id) {
        return wgRepository.findById(id);
    }

    /**
     * Retrieves all WG entities.
     *
     * @return An Iterable of all WG entities.
     */
    public Iterable<WG> getAll() {
        return wgRepository.findAll();
    }

    /**
     * Saves a WG entity to the repository.
     * If the WG already exists, it will be updated; otherwise, a new WG will be created.
     *
     * @param wg The WG entity to be saved.
     * @return The saved WG entity.
     */
    public WG save(WG wg) {
        return wgRepository.save(wg);
    }
}
