package bg.sofia.uni.fmi.mjt.uno.card.exception;

public class InvalidGameException extends RuntimeException {

    public InvalidGameException(String message) {
        super(message);
    }

    public InvalidGameException(String message, Throwable cause) {
        super(message, cause);
    }

}
