package nccloud.utils.collection;


import java.io.Serializable;
import java.util.*;

public class MapSet<K extends Serializable, V extends Serializable> implements Serializable, Cloneable {
    private static final long serialVersionUID = 3235650078973528702L;
    private Map<K, Set<V>> map = new HashMap();

    public MapSet() {
    }

    public void clear() {
        this.map.clear();
    }

    public Set<K> keySet(){
        return map.keySet();
    }

    public void put(K key,V value){
        Set<V> set = (Set)this.map.get(key);
        if (set == null) {
            set = new HashSet();
        }
        set.add(value);
        this.map.put(key, set);
    }

    public void putSet(K key, Collection<V>  value){
        Set<V> set = new HashSet<>(value);
        this.map.put(key, set);
    }
    public void addAll(K key, Collection<V>  value){
        Set<V> set = (Set)this.map.get(key);
        if (set == null) {
            set = new HashSet();
        }
        set.addAll(value);
        this.map.put(key, set);
    }

    public void set(K key,Set<V> value){
        this.map.put(key, value);
    }


    public Set<V> get(K key) {
        return this.map.get(key);
    }
    public Set<V> getNotNull(K key) {
        Set<V> vs = this.map.get(key);
        if (vs==null){
            vs = new HashSet<>();
            this.map.put(key,vs);
        }
        return vs;
    }
    public Set<V> getNotNullNOSet(K key) {
        Set<V> vs = this.map.get(key);
        return vs==null?new HashSet<>():vs;
    }

    public boolean isEmpty(){
        return map.isEmpty();
    }
    public boolean isEmptySet(K key1){
        return map.isEmpty() || map.get(key1)==null ||  map.get(key1).isEmpty();
    }

    public Set<V> valuesFlat(){
        Set<V> temp = new HashSet<>();
        map.values().forEach(set->{
            temp.addAll(set);
        });
        return temp;
    }

    public Collection<Set<V>> values(){
        return map.values();
    }

    public void remove(K key,V object){
        Set<V> vs = map.get(key);
        if (vs!=null) {
            vs.remove(object);
            if (vs.isEmpty()){
                map.remove(key);
            }
        }
    }
    public void removeSet(K key){
        map.remove(key);

    }

    public void tryInit(K key,Set<V> set){
        if (map.get(key)==null){
            map.put(key,set);
        }
    }

    public void marge(MapSet<K,V> mapSet){
        for (K key : mapSet.keySet()) {
            Set<V> vs = mapSet.get(key);
            addAll(key,vs);
        }
    }

    public boolean isContains(K key1,V value){
        Set<V> vs = map.get(key1);
        if (vs==null) return false;
        return vs.contains(value);
    }

}