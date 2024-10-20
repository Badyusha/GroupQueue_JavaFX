package by.bsuir.groupqueuefx.exceptions.entityExceptions;

public class DayOfWeekException extends RuntimeException {
	public DayOfWeekException(String errorMessage) {
		super("DayOfWeek exception: " + errorMessage);
	}
}
