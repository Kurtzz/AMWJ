package pl.edu.agh.amwj.exceptions;

/**
 * Created by Comarch on 2016-11-28.
 */
public class UndeclaredVariableException extends Exception{
    public UndeclaredVariableException() {
    }

    public UndeclaredVariableException(String message) {
        super(message);
    }

    public UndeclaredVariableException(String message, Throwable cause) {
        super(message, cause);
    }

    public UndeclaredVariableException(Throwable cause) {
        super(cause);
    }

    public UndeclaredVariableException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
