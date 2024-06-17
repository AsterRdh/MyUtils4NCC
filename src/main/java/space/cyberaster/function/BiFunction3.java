package space.cyberaster.function;
@FunctionalInterface
public interface BiFunction3<T, U, E, R> {
    public R apply(T t, U u, E e);
}
