package by.bsuir.groupqueuefx.exceptions;

public class ScheduleException extends RuntimeException {
	public ScheduleException(String errorMessage) {
		super("Schedule exception: " + errorMessage);
	}
}
