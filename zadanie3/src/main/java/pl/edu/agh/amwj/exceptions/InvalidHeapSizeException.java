package pl.edu.agh.amwj.exceptions;

/**
 * Created by Kurtzz on 2016-11-28.
 */
public class InvalidHeapSizeException extends Exception {
    public InvalidHeapSizeException() {
    }

    public InvalidHeapSizeException(String message) {
        super(message);
    }

    public InvalidHeapSizeException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidHeapSizeException(Throwable cause) {
        super(cause);
    }

    public InvalidHeapSizeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
