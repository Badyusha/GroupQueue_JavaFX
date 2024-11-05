package by.bsuir.exceptions.entityExceptions;

public class PermissionException extends RuntimeException {
	public PermissionException(String error) {
		super("Permission exception: " + error);
	}
}
