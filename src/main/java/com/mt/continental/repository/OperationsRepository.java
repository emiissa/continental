package com.mt.continental.repository;

import com.mt.continental.domain.Operations;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Operations entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OperationsRepository extends JpaRepository<Operations,Long> {
    
}
