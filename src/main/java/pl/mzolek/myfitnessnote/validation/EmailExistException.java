package pl.mzolek.myfitnessnote.validation;

public class EmailExistException extends Exception {
    public EmailExistException(String message) {
        super(message);
    }
}
