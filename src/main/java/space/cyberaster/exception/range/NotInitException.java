package space.cyberaster.exception.range;

public class NotInitException extends RangeException {
    public NotInitException() {
        super();
    }

    public NotInitException(String message) {
        super(message);
    }

    public NotInitException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotInitException(Throwable cause) {
        super(cause);
    }

    protected NotInitException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
