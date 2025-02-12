package bg.sofia.uni.fmi.mjt.uno.card.exception;

public class GameAlreadyStartedException extends RuntimeException {

    public GameAlreadyStartedException(String message) {
        super(message);
    }

    public GameAlreadyStartedException(String message, Throwable cause) {
        super(message, cause);
    }

}
