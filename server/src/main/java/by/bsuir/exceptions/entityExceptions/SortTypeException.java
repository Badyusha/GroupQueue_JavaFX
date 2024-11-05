package by.bsuir.exceptions.entityExceptions;

public class SortTypeException extends RuntimeException {
	public SortTypeException(String error) {
		super("Sort type exception: " + error);
	}
}
