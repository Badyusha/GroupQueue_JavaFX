package by.bsuir.groupqueuefx.exceptions;

public class SortTypeException extends RuntimeException {
	public SortTypeException(String error) {
		super("Sort type exception: " + error);
	}
}
