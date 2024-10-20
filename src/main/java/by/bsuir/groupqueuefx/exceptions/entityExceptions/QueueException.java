package by.bsuir.groupqueuefx.exceptions.entityExceptions;

public class QueueException extends RuntimeException {
	public QueueException(String error) {
		super("Queue exception: " + error);
	}
}
