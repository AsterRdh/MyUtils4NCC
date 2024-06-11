package nccloud.function;

@FunctionalInterface
public interface Map3Consumer<K1,K2,K3,V> {
    void accept(K1 k1,K2 k2,K3 k3,V value);
}
