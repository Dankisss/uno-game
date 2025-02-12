package bg.sofia.uni.fmi.mjt.uno.card.exception;

public class InvalidGameOwnerException extends RuntimeException {

    public InvalidGameOwnerException(String message) {
        super(message);
    }

    public InvalidGameOwnerException(String message, Throwable cause) {
        super(message, cause);
    }

}
