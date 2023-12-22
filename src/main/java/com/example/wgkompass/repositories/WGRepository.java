package com.example.wgkompass.repositories;

import com.example.wgkompass.models.WG;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The WGRepository interface handles the operations for storage, retrieval,
 * update, and delete of WG entities.
 */
@Repository
public interface WGRepository extends JpaRepository<WG, Long> {
}