package com.authapi.repository;

import com.authapi.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * UserRepository
 *
 * <p>Spring Data JPA repository for managing User entities.
 *
 * <p>• Inherits standard CRUD methods from JpaRepository<User, Long>. • Adds a custom finder:
 * findByEmail(String email) to retrieve a user by their email address.
 */
public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByEmail(String email);
}
