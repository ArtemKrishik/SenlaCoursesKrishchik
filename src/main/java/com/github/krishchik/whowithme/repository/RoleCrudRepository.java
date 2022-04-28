package com.github.krishchik.whowithme.repository;

import com.github.krishchik.whowithme.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleCrudRepository extends JpaRepository<Role, Long> {
}
