package br.com.clubedoalbum.identity.controller;

import br.com.clubedoalbum.identity.dto.CreateUserRequest;
import br.com.clubedoalbum.identity.dto.UserResponse;
import br.com.clubedoalbum.identity.service.UserService;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping
  public ResponseEntity<UserResponse> create(@Valid @RequestBody CreateUserRequest request) {
    UserResponse response = UserResponse.from(userService.create(request));

    return ResponseEntity
        .created(URI.create("/users/" + response.id()))
        .body(response);
  }

  @GetMapping
  public List<UserResponse> list() {
    return userService.list().stream()
        .map(UserResponse::from)
        .toList();
  }

  @GetMapping("/{id}")
  public ResponseEntity<UserResponse> findById(@PathVariable UUID id) {
    return userService.findById(id)
        .map(UserResponse::from)
        .map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());
  }
}
