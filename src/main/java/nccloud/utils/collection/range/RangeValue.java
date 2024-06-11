package nccloud.utils.collection.range;

import java.util.Set;

public class RangeValue<K,V>{
    private V value;
    private Set<K> keys;

    public RangeValue(V value, Set<K> keys) {
        this.value = value;
        this.keys = keys;
    }

    public Set<K> getKeys() {
        return keys;
    }

    public void setKeys(Set<K> keys) {
        this.keys = keys;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }
}
