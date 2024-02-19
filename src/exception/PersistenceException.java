package exception;

public class PersistenceException extends RuntimeException {

    public PersistenceException() {
        super();
    }

    public PersistenceException(String reason) {
        super(reason);
    }

    public PersistenceException(String message, Throwable cause) {
        super(message, cause);
    }

    public PersistenceException(Throwable cause) {
        super(cause);
    }
}