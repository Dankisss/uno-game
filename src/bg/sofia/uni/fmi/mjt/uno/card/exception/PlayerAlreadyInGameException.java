package bg.sofia.uni.fmi.mjt.uno.card.exception;

public class PlayerAlreadyInGameException extends RuntimeException {

    public PlayerAlreadyInGameException(String message) {
        super(message);
    }

    public PlayerAlreadyInGameException(String message, Throwable cause) {
        super(message, cause);
    }

}
