package by.bsuir.groupqueuefx.exceptions;

public class QueueException extends RuntimeException {
	public QueueException(String error) {
		super("Queue exception: " + error);
	}
}
