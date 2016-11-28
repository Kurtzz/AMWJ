package pl.edu.agh.amwj.exceptions;

/**
 * Created by Kurtzz on 2016-11-28.
 */
public class InvalidVariableNameException extends Exception {
    public InvalidVariableNameException() {
    }

    public InvalidVariableNameException(String message) {
        super(message);
    }

    public InvalidVariableNameException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidVariableNameException(Throwable cause) {
        super(cause);
    }

    public InvalidVariableNameException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
