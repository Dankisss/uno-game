package bg.sofia.uni.fmi.mjt.uno.card.exception;

public class GameCouldNotStartException extends RuntimeException {

    public GameCouldNotStartException(String message) {
        super(message);
    }

    public GameCouldNotStartException(String message, Throwable cause) {
        super(message, cause);
    }

}
