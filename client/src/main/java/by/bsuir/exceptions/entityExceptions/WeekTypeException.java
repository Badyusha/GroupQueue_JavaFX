package by.bsuir.exceptions.entityExceptions;

public class WeekTypeException extends RuntimeException {
	public WeekTypeException(String errorMessage) {
		super("WeekType exception: " + errorMessage);
	}
}
