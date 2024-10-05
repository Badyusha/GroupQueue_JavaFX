package by.bsuir.groupqueuefx.exceptions;

public class RoleTypeException extends RuntimeException {
	public RoleTypeException(String errorMessage) {
		super("RoleType exception: " + errorMessage);
	}
}
