package space.cyberaster.function;

import space.cyberaster.exception.range.RangeException;

@FunctionalInterface
public interface NotFoundFunction<T> {

    void accept(T t) throws RangeException;
}
