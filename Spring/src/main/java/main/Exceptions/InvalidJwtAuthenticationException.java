package main.Exceptions;

public class InvalidJwtAuthenticationException extends RuntimeException{
    public InvalidJwtAuthenticationException(String message) {
        super(message);
    }
}
