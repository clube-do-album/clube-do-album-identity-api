package br.com.clubedoalbum.identity.controller;

import br.com.clubedoalbum.identity.dto.HealthResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

  @GetMapping("/health")
  public HealthResponse health() {
    return new HealthResponse("clube-do-album-identity-api", "UP");
  }
}
