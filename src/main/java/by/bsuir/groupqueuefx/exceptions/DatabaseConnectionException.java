package by.bsuir.groupqueuefx.exceptions;

public class DatabaseConnectionException extends RuntimeException {
	public DatabaseConnectionException(String message) {
		super("Database connection exception: " + message);
	}
}
