package space.cyberaster.exception.range;

public class IsEndException extends RangeException {
    public IsEndException() {
        super();
    }

    public IsEndException(String message) {
        super(message);
    }

    public IsEndException(String message, Throwable cause) {
        super(message, cause);
    }

    public IsEndException(Throwable cause) {
        super(cause);
    }

    protected IsEndException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
