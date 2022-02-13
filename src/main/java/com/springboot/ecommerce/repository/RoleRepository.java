package com.springboot.ecommerce.repository;

import com.springboot.ecommerce.model.ERole;
import com.springboot.ecommerce.model.Role;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
  Optional<Role> findByName(ERole name);
}
