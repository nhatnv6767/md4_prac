package com.ra.repository;

import com.ra.model.entities.Position;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PositionRepository extends BaseRepository<Position> {
    boolean existsByName(String name);

    @Query("SELECT p FROM Position p where p.status = true AND (:name IS null or lower(p.name) like lower(concat('%', :name, '%') ) )")
    // The searchPositions() method is used to search for Positions by name
    // Parameters:
    // - @Param("name") String name: name to search for, can be null
    // - Pageable pageable: pagination object
    //
    // JPQL query:
    // - SELECT p FROM Position p: get all information from Position table
    // - p.status = true: only get active Positions
    // - :name IS null: if name parameter is null, this condition is always true
    // - lower(p.name) like lower(concat('%', :name, '%')):
    // + Case-insensitive name comparison
    // + Search by contains (contains substring)
    // + Example: name="dev" will match "Developer", "development",...
    //
    // Returns:
    // - Page<Position>: paginated object containing list of Positions matching
    // conditions
    Page<Position> searchPositions(@Param("name") String name, Pageable pageable);
}
