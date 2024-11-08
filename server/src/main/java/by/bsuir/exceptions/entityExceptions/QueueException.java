package by.bsuir.exceptions.entityExceptions;

public class QueueException extends Exception {
	public QueueException(String error) {
		super("Queue exception: " + error);
	}
}
