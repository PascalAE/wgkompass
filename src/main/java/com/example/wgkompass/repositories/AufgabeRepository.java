package com.example.wgkompass.repositories;

import com.example.wgkompass.models.Aufgabe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The AufgabeRepository interface provides the mechanism for storage, retrieval,
 * update, and delete operations on Aufgabe objects.
 */
@Repository
public interface AufgabeRepository extends JpaRepository<Aufgabe, Long>{
    List<Aufgabe> findAllByWgId(Long wgId);
}