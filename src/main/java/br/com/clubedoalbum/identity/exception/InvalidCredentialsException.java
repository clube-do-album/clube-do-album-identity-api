package br.com.clubedoalbum.identity.exception;

public class InvalidCredentialsException extends RuntimeException {

  public InvalidCredentialsException() {
    super("invalid email or password.");
  }
}
