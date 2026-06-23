package br.com.clubedoalbum.identity.repository;

import br.com.clubedoalbum.identity.domain.User;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, UUID> {

  boolean existsByEmailIgnoreCase(String email);

  Optional<User> findByEmailIgnoreCase(String email);

  List<User> findTop10ByNameContainingIgnoreCaseOrEmailContainingIgnoreCaseOrderByNameAsc(String name, String email);

  List<User> findByIdIn(List<UUID> ids);
}
