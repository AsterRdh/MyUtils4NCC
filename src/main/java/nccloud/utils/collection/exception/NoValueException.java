package nccloud.utils.collection.exception;

public class NoValueException extends Exception {
    public NoValueException() {
        super();
    }

    public NoValueException(String message) {
        super(message);
    }

    public NoValueException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoValueException(Throwable cause) {
        super(cause);
    }

    protected NoValueException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
