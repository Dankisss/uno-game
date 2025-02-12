package bg.sofia.uni.fmi.mjt.uno.card.exception;

public class PlayerIsNotRegisteredException extends RuntimeException {

    public PlayerIsNotRegisteredException(String message) {
        super(message);
    }

    public PlayerIsNotRegisteredException(String message, Throwable cause) {
        super(message, cause);
    }

}
