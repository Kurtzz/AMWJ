package pl.edu.agh.amwj.exceptions;

/**
 * Created by Comarch on 2016-11-28.
 */
public class ForbiddenCharacterException extends Exception {
    public ForbiddenCharacterException() {
    }

    public ForbiddenCharacterException(String message) {
        super(message);
    }

    public ForbiddenCharacterException(String message, Throwable cause) {
        super(message, cause);
    }

    public ForbiddenCharacterException(Throwable cause) {
        super(cause);
    }

    public ForbiddenCharacterException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
