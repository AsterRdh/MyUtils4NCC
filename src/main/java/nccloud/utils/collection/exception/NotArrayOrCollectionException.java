package nccloud.utils.collection.exception;

/**
 * 不是数组异常
 */
public class NotArrayOrCollectionException  extends Exception{
    public NotArrayOrCollectionException() {
    }

    public NotArrayOrCollectionException(String message) {
        super(message);
    }

    public NotArrayOrCollectionException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotArrayOrCollectionException(Throwable cause) {
        super(cause);
    }

    public NotArrayOrCollectionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
