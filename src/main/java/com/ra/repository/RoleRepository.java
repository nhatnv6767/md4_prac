package com.ra.repository;

import com.ra.model.entities.Role;
import com.ra.model.enums.RoleType;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends BaseRepository<Role> {
    Optional<Role> findByName(RoleType name);
}
