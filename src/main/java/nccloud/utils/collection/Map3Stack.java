package nccloud.utils.collection;
import java.util.*;
import java.util.function.Function;

public class Map3Stack<K1,K2,K3,V> {
    Map<K1,MapMapStack<K2,K3,V>> map = new HashMap<>();

    public void add(K1 k1,K2 k2,K3 k3, V v) {
        map.computeIfAbsent(k1,k->new MapMapStack<>()).add(k2,k3,v);
    }

    public void push(K1 k1,K2 k2,K3 k3, V v) {
        map.computeIfAbsent(k1,k->new MapMapStack<>()).push(k2,k3,v);
    }

    public MapMapStack<K2,K3,V> get(K1 k1) {
        return map.get(k1);
    }
    public MapStack<K3,V> get(K1 k1,K2 k2) {
        MapMapStack<K2, K3, V> k2K3VMapMapStack = map.get(k1);
        if (k2K3VMapMapStack == null) return null;
        return k2K3VMapMapStack.get(k2);
    }
    public Stack<V> get(K1 k1,K2 k2,K3 k3) {
        MapMapStack<K2, K3, V> k2K3VMapMapStack = map.get(k1);
        if (k2K3VMapMapStack == null) return null;
        return  k2K3VMapMapStack.get(k2,k3);
    }

    public V pop(K1 k1,K2 k2,K3 k3) {
        Stack<V> vs = get(k1, k2, k3);
        if (vs==null) return null;
        return vs.pop();
    }


    public V peek(K1 k1,K2 k2,K3 k3) {
        Stack<V> vs = get(k1, k2, k3);
        if (vs==null) return null;
        return vs.peek();
    }

    public boolean isEmpty(){
        return map.isEmpty();
    }

    public boolean isEmpty(K1 k1) {
        MapMapStack<K2, K3, V> k2K3VMapMapStack = map.get(k1);
        return k2K3VMapMapStack == null || k2K3VMapMapStack.isEmpty();
    }
    public boolean isEmpty(K1 k1,K2 k2) {
        MapMapStack<K2, K3, V> k2K3VMapMapStack = map.get(k1);
        if(k2K3VMapMapStack == null || k2K3VMapMapStack.isEmpty()) return true;
        return k2K3VMapMapStack.isEmpty(k2);
    }
    public boolean isEmpty(K1 k1,K2 k2,K3 k3) {
        MapMapStack<K2, K3, V> k2K3VMapMapStack = map.get(k1);
        if(k2K3VMapMapStack == null || k2K3VMapMapStack.isEmpty()) return true;
        return k2K3VMapMapStack.isEmpty(k2,k3);
    }

    public int search(K1 k1,K2 k2,K3 k3,V v) {
        Stack<V> vs = get(k1, k2, k3);
        if(vs == null || vs.isEmpty()) return -1;
        return vs.search(v);
    }

    public void clear() {
        this.map.clear();
    }

    public Set<K1> keySet(){
        return map.keySet();
    }
    public Set<K2> keySet(K1 k1){
        MapMapStack<K2, K3, V> k2K3VMapMapStack = map.get(k1);
        if(k2K3VMapMapStack==null) return null;
        return k2K3VMapMapStack.keySet();
    }
    public Set<K3> keySet(K1 k1,K2 k2){
        MapStack<K3, V> k3VMapStack = get(k1, k2);
        if(k3VMapStack==null) return null;
        return k3VMapStack.keySet();
    }

    public void remove(K1 k1){
        map.remove(k1);
    }
    public void remove(K1 k1,K2 k2){
        MapMapStack<K2, K3, V> k2K3VMapMapStack = get(k1);
        if(k2K3VMapMapStack==null) return;
        k2K3VMapMapStack.remove(k2);
    }

    public void remove(K1 k1,K2 k2,K3 k3){
        MapStack<K3, V> k3VMapStack = get(k1, k2);
        if(k3VMapStack==null) return;
        k3VMapStack.remove(k3);
    }


    public Stack<V> computeIfAbsent(K1 k1,K2 k2,K3 k3, Function<K3,Stack<V>> mappingFunction){
        MapMapStack<K2, K3, V> k2K3VMapMapStack = map.computeIfAbsent(k1, k -> new MapMapStack<>());
        return k2K3VMapMapStack.computeIfAbsent(k2,k3,mappingFunction);
    }



}
