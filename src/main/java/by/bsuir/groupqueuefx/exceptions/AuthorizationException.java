package by.bsuir.groupqueuefx.exceptions;

public class AuthorizationException extends Exception {
	public AuthorizationException(String errorMessage) {
		super("Failed to authorize: " + errorMessage);
	}
}
