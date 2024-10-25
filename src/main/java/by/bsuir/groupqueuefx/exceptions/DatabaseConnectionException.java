package by.bsuir.groupqueuefx.exceptions;

public class DatabaseConnectionException extends Exception {
	public DatabaseConnectionException(String message) {
		super("Database connection exception: " + message);
	}
}
