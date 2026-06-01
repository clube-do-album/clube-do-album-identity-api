package br.com.clubedoalbum.identity.dto;

import java.time.Instant;
import java.util.List;

public record ErrorResponse(
    Instant timestamp,
    String message,
    List<String> details
) {}
