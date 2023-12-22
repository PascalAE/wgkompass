package com.example.wgkompass.repositories;

import com.example.wgkompass.models.Mitglied;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The MitgliedRepository interface handles the operations for storage, retrieval,
 * update, and delete of Mitglied entities.
 */
@Repository
public interface MitgliedRepository extends JpaRepository<Mitglied, Long>{
    List<Mitglied> findAllByWgId(Long wgId);
}