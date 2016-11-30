package pl.edu.agh.amwj.exceptions;

/**
 * Created by Kurtzz on 2016-11-28.
 */
public class InvalidIdentifierException extends Exception {
    public InvalidIdentifierException() {
    }

    public InvalidIdentifierException(String message) {
        super(message);
    }

    public InvalidIdentifierException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidIdentifierException(Throwable cause) {
        super(cause);
    }

    public InvalidIdentifierException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
