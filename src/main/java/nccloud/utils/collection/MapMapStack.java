package nccloud.utils.collection;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.function.Function;

public class MapMapStack<K1,K2,V> {
    Map<K1,MapStack<K2,V>> map = new HashMap<K1,MapStack<K2,V>>();

    public void add(K1 k1,K2 k2, V v) {
        MapStack<K2,V> vs = map.computeIfAbsent(k1, k -> new MapStack<>());
        vs.add(k2,v);
    }

    public void push(K1 k1,K2 k2, V v) {
        MapStack<K2,V> vs = map.computeIfAbsent(k1, k -> new MapStack<>());
        vs.push(k2,v);
    }

    public MapStack<K2,V> get(K1 k1) {
        return map.get(k1);
    }

    public Stack<V> get(K1 k1,K2 k2) {
        MapStack<K2, V> k2VMapStack = get(k1);
        if (k2VMapStack==null) return null;
        return k2VMapStack.get(k2);
    }

    public V pop(K1 k1,K2 k2) {
        MapStack<K2,V> k2VMapStack = get(k1);
        if (k2VMapStack==null) return null;
        return k2VMapStack.pop(k2);
    }

    public V peek(K1 k1,K2 k2) {
        MapStack<K2,V> k2VMapStack = get(k1);
        if (k2VMapStack==null) return null;
        return k2VMapStack.peek(k2);
    }

    public boolean isEmpty(){
        return map.isEmpty();
    }

    public boolean isEmpty(K1 k1) {
        MapStack<K2, V> k2VMapStack = map.get(k1);
        return k2VMapStack == null || k2VMapStack.isEmpty();
    }
    public boolean isEmpty(K1 k1,K2 k2) {
        MapStack<K2, V> k2VMapStack = map.get(k1);
        if(k2VMapStack == null || k2VMapStack.isEmpty()) return true;
        return k2VMapStack.isEmpty(k2);
    }

    public int search(K1 k1,K2 k2,V v) {
        MapStack<K2, V> k2VMapStack = map.get(k1);
        if(k2VMapStack == null || k2VMapStack.isEmpty()) return -1;
        return k2VMapStack.search(k2,v);
    }

    public void clear() {
        this.map.clear();
    }

    public Set<K1> keySet(){
        return map.keySet();
    }
    public Set<K2> keySet(K1 k1){
        MapStack<K2, V> k2VMapStack = map.get(k1);
        if(k2VMapStack==null) return null;
        return k2VMapStack.keySet();
    }

    public void remove(K1 k1){
        map.remove(k1);
    }
    public void remove(K1 k1,K2 k2){
        MapStack<K2, V> k2VMapStack = map.get(k1);
        if(k2VMapStack==null) return;
        k2VMapStack.remove(k2);
    }


    public Stack<V> computeIfAbsent(K1 k1,K2 k2, Function<K2,Stack<V>> mappingFunction){
        MapStack<K2, V> k2VMapStack = map.computeIfAbsent(k1, k -> new MapStack<>());
        return k2VMapStack.computeIfAbsent(k2,mappingFunction);
    }

}
