package nccloud.function;

@FunctionalInterface
public interface Map2Consumer<K1,K2,V> {
    void accept(K1 k1,K2 k2,V value);
}
