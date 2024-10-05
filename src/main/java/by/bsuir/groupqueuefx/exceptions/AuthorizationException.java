package by.bsuir.groupqueuefx.exceptions;

public class AuthorizationException extends RuntimeException {
	public AuthorizationException(String errorMessage) {
		super("Failed to authorize: " + errorMessage);
	}
}
