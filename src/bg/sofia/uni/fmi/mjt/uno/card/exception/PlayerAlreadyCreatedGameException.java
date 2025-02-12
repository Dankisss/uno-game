package bg.sofia.uni.fmi.mjt.uno.card.exception;

public class PlayerAlreadyCreatedGameException extends RuntimeException {

    public PlayerAlreadyCreatedGameException(String message) {
        super(message);
    }

    public PlayerAlreadyCreatedGameException(String message, Throwable cause) {
        super(message, cause);
    }

}
