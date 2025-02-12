package bg.sofia.uni.fmi.mjt.uno.card.exception;

public class GameNotStartedException extends RuntimeException {

    public GameNotStartedException(String message) {
        super(message);
    }

    public GameNotStartedException(String message, Throwable cause) {
        super(message, cause);
    }

}
