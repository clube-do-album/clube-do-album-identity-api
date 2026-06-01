package br.com.clubedoalbum.identity.service;

import br.com.clubedoalbum.identity.domain.User;
import br.com.clubedoalbum.identity.dto.LoginRequest;
import br.com.clubedoalbum.identity.dto.LoginResponse;
import br.com.clubedoalbum.identity.dto.UserResponse;
import br.com.clubedoalbum.identity.exception.InvalidCredentialsException;
import br.com.clubedoalbum.identity.repository.UserRepository;
import java.util.Locale;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;

  public AuthService(
      UserRepository userRepository,
      PasswordEncoder passwordEncoder,
      JwtService jwtService
  ) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.jwtService = jwtService;
  }

  @Transactional(readOnly = true)
  public LoginResponse login(LoginRequest request) {
    User user = userRepository.findByEmailIgnoreCase(normalizeEmail(request.email()))
        .orElseThrow(InvalidCredentialsException::new);

    if (!passwordEncoder.matches(request.password(), user.getPasswordHash())) {
      throw new InvalidCredentialsException();
    }

    return new LoginResponse(
        jwtService.generateToken(user),
        "Bearer",
        jwtService.getExpirationSeconds(),
        UserResponse.from(user)
    );
  }

  private String normalizeEmail(String email) {
    return email.trim().toLowerCase(Locale.ROOT);
  }
}
