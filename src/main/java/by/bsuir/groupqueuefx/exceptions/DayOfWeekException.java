package by.bsuir.groupqueuefx.exceptions;

public class DayOfWeekException extends RuntimeException {
	public DayOfWeekException(String errorMessage) {
		super("DayOfWeek exception: " + errorMessage);
	}
}
