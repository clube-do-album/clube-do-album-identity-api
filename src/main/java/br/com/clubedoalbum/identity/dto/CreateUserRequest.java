package br.com.clubedoalbum.identity.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateUserRequest(
    @NotBlank(message = "name is required.")
    @Size(max = 120, message = "name must have at most 120 characters.")
    String name,

    @NotBlank(message = "email is required.")
    @Email(message = "email must be valid.")
    @Size(max = 180, message = "email must have at most 180 characters.")
    String email,

    @NotBlank(message = "password is required.")
    @Size(min = 6, max = 72, message = "password must have between 6 and 72 characters.")
    String password
) {}
