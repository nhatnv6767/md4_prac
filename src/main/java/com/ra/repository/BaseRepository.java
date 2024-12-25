package com.ra.repository;

import com.ra.model.entities.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface BaseRepository<T extends BaseEntity> extends JpaRepository<T, Long> {
    // Find all active entities
    // This query selects all entities from the current entity type (#{#entityName})
    // where the status field is true
    // The 'e' is an alias for the entity in the query
    // For example: "SELECT e FROM Role e WHERE e.status = true"
    // This returns all active records from the table
    @Query("SELECT e FROM #{#entityName} e WHERE e.status = true")
    List<T> findAllActive();

    // Find active entity by id
    // This query selects a single entity from the current entity type
    // where both the id matches the provided parameter AND status is true
    // The :id is a named parameter that gets replaced with the actual id value
    // For example: "SELECT e FROM Role e WHERE e.id = 1 AND e.status = true"
    // This returns a single active record matching the id
    @Query("SELECT e FROM #{#entityName} e WHERE e.id = :id AND e.status = true")
    Optional<T> findActiveById(@Param("id") Long id);
}
