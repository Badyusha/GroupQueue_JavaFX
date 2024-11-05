package by.bsuir.exceptions;

public class PasswordsNotMatchesException extends AuthorizationException {
    public PasswordsNotMatchesException(String message) {
        super(message);
    }
}
