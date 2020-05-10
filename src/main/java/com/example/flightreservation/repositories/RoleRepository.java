package com.example.flightreservation.repositories;

import com.example.flightreservation.domains.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role,Long> {
}
