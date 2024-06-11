package nccloud.utils.collection;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.function.Function;

public class MapStack <K,V>{
    private Map<K, Stack<V>> map = new HashMap<K, Stack<V>>();

    public void add(K k1, V v) {
        Stack<V> vs = map.computeIfAbsent(k1, k -> new Stack<>());
        vs.add(v);
    }

    public void push(K k1, V v) {
        Stack<V> vs = map.computeIfAbsent(k1, k -> new Stack<>());
        vs.push(v);
    }

    public Stack<V> get(K k1) {
        return map.get(k1);
    }

    public V pop(K k1) {
        Stack<V> vs = map.get(k1);
        if(vs == null || vs.isEmpty()) return null;
        return vs.pop();
    }

    public V popWithRemove(K k1) {
        Stack<V> vs = map.get(k1);
        if(vs == null || vs.isEmpty()) return null;
        V pop = vs.pop();
        if(vs.isEmpty()) map.remove(k1);
        return pop;
    }

    public V peek(K k1) {
        Stack<V> vs = map.get(k1);
        if(vs == null || vs.isEmpty()) return null;
        return vs.peek();
    }

    public boolean isEmpty(){
        return map.isEmpty();
    }

    public boolean isEmpty(K k1) {
        Stack<V> vs = map.get(k1);
        return vs == null || vs.isEmpty();
    }

    public int search(K k1,V v) {
        Stack<V> vs = map.get(k1);
        if(vs == null || vs.isEmpty()) return -1;
        return vs.search(v);
    }

    public void clear() {
        this.map.clear();
    }

    public Set<K> keySet(){
        return map.keySet();
    }

    public void remove(K key){
        map.remove(key);
    }

    public int size(){
        return map.size();
    }

    public Stack<V> computeIfAbsent(K k1, Function<K,Stack<V>> mappingFunction){
        return map.computeIfAbsent(k1, mappingFunction);
    }










}
