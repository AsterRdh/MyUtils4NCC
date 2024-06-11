package nccloud.utils.sql;

@FunctionalInterface
public interface SqlConsumer<T,E,R> {
    R accept(T t, E e);
}
