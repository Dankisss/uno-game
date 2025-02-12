package bg.sofia.uni.fmi.mjt.uno.card.exception;

public class PlayerNotLoggedInException extends RuntimeException {

    public PlayerNotLoggedInException(String message) {
        super(message);
    }

    public PlayerNotLoggedInException(String message, Throwable cause) {
        super(message, cause);
    }

}
