package pl.edu.agh.amwj.exceptions;

/**
 * Created by Comarch on 2016-11-28.
 */
public class VariableAlreadyDefinedException extends RuntimeException {
    public VariableAlreadyDefinedException() {
    }

    public VariableAlreadyDefinedException(String message) {
        super(message);
    }

    public VariableAlreadyDefinedException(String message, Throwable cause) {
        super(message, cause);
    }

    public VariableAlreadyDefinedException(Throwable cause) {
        super(cause);
    }

    public VariableAlreadyDefinedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
