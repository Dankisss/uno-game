package bg.sofia.uni.fmi.mjt.uno.card.exception;

public class InvalidCardException extends RuntimeException {

    public InvalidCardException(String message) {
        super(message);
    }

    public InvalidCardException(String message, Throwable cause) {
        super(message, cause);
    }
}
