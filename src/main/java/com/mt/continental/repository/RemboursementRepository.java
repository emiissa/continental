package com.mt.continental.repository;

import com.mt.continental.domain.Remboursement;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Remboursement entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RemboursementRepository extends JpaRepository<Remboursement,Long> {
    
}
