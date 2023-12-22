package com.example.wgkompass.repositories;

import com.example.wgkompass.models.Inventar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The InventarRepository interface handles the operations for storage, retrieval,
 * update, and delete of Inventar entities.
 */
@Repository
public interface InventarRepository extends JpaRepository<Inventar, Long>{
    List<Inventar> findAllByWgId(Long wgId);
}