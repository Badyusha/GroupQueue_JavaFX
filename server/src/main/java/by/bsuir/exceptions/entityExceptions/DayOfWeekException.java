package by.bsuir.exceptions.entityExceptions;

public class DayOfWeekException extends RuntimeException {
	public DayOfWeekException(String errorMessage) {
		super("DayOfWeek exception: " + errorMessage);
	}
}
