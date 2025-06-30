package dev.nikhilj.authservice.repositories;

import dev.nikhilj.authservice.entitites.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	public boolean existsByEmail(String email);

	public boolean existsByUsername(String username);

	public Optional<User> findByUsername(String username);

	@Query("SELECT u FROM User u WHERE u.email = :identifier OR u.username = :identifier")
	public Optional<User> findByUsernameOrEmail(@Param("identifier") String identifier);
}
