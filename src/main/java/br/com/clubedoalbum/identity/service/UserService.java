package br.com.clubedoalbum.identity.service;

import br.com.clubedoalbum.identity.domain.User;
import br.com.clubedoalbum.identity.dto.CreateUserRequest;
import br.com.clubedoalbum.identity.repository.UserRepository;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  @Transactional
  public User create(CreateUserRequest request) {
    String email = normalizeEmail(request.email());

    if (userRepository.existsByEmailIgnoreCase(email)) {
      throw new IllegalArgumentException("email already registered.");
    }

    String passwordHash = passwordEncoder.encode(request.password());
    User user = new User(request.name().trim(), email, passwordHash);

    return userRepository.save(user);
  }

  @Transactional(readOnly = true)
  public List<User> list() {
    return userRepository.findAll();
  }

  @Transactional(readOnly = true)
  public List<User> search(String query) {
    String normalizedQuery = query == null ? "" : query.trim();

    if (normalizedQuery.isBlank()) {
      return List.of();
    }

    return userRepository.findTop10ByNameContainingIgnoreCaseOrEmailContainingIgnoreCaseOrderByNameAsc(
        normalizedQuery,
        normalizedQuery
    );
  }

  @Transactional(readOnly = true)
  public List<User> findByIds(List<UUID> ids) {
    if (ids.isEmpty()) {
      return List.of();
    }

    return userRepository.findByIdIn(ids.stream().distinct().limit(50).toList());
  }

  @Transactional(readOnly = true)
  public Optional<User> findById(UUID id) {
    return userRepository.findById(id);
  }

  private String normalizeEmail(String email) {
    return email.trim().toLowerCase(Locale.ROOT);
  }
}
