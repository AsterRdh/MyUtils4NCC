package nccloud.utils.functions;

@FunctionalInterface
public interface ConsumerWithException<T,EX extends Exception> {
    void accept(T t) throws EX;

}
