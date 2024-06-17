package space.cyberaster.exception.range;

public class NoValueException extends RangeException {
    protected NoValueException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public NoValueException(Throwable cause) {
        super(cause);
    }

    public NoValueException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoValueException(String message) {
        super(message);
    }

    public NoValueException() {
        super();
    }
}
