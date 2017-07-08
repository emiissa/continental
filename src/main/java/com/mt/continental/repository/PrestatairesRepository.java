package com.mt.continental.repository;

import com.mt.continental.domain.Prestataires;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Prestataires entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PrestatairesRepository extends JpaRepository<Prestataires,Long> {
    
}
