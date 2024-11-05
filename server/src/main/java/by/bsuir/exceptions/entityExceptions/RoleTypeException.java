package by.bsuir.exceptions.entityExceptions;

public class RoleTypeException extends RuntimeException {
	public RoleTypeException(String errorMessage) {
		super("RoleType exception: " + errorMessage);
	}
}
