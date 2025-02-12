package bg.sofia.uni.fmi.mjt.uno.card.exception;

public class PlayerNotOnTurnException extends RuntimeException {

    public PlayerNotOnTurnException(String message) {
        super(message);
    }

    public PlayerNotOnTurnException(String message, Throwable cause) {
        super(message, cause);
    }

}
