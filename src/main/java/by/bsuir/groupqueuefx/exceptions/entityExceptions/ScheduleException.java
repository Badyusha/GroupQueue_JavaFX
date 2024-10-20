package by.bsuir.groupqueuefx.exceptions.entityExceptions;

public class ScheduleException extends RuntimeException {
	public ScheduleException(String errorMessage) {
		super("Schedule exception: " + errorMessage);
	}
}
