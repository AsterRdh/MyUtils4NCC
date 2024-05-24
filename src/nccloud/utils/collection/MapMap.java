package nccloud.utils.collection;

import nccloud.function.Map2Consumer;

import java.util.*;

public class MapMap<K1,K2,V> {
    protected Map<K1, Map<K2,V>> map;

    public MapMap() {
        this.map = new HashMap<>();
    }

    public void put(K1 key,Map<K2,V> value){
        map.put(key,value);
    }

    public void put(K1 key,K2 key2,V value){
        Map<K2, V> k2VMap = map.get(key);
        if (k2VMap==null) k2VMap=new HashMap<>();
        k2VMap.put(key2,value);
        map.put(key,k2VMap);
    }

    public Map<K2,V> getK2Map(K1 key){
        return map.get(key);
    }
    public Map<K2,V> getK2MapNotNull(K1 key){
        Map<K2, V> k2VMap = map.get(key);
        if (k2VMap== null)k2VMap = new HashMap<>();
        map.put(key,k2VMap);
        return k2VMap;
    }

    public V get (K1 key1,K2 key2){
        Map<K2, V> k2VMap = map.get(key1);
        if (k2VMap==null) return null;
        return k2VMap.get(key2);
    }

    public Map<K1, Map<K2,V>> getMap(){
        return map;
    }

    public Set<K1> getK1Set(){
        return map.keySet();
    }
    public Set<K2> getK2Set(K1 key1){
        Map<K2, V> k2VMap = map.get(key1);
        if (k2VMap==null) return null;
        return k2VMap.keySet();
    }
    public Set<K2> getK2SetNotNull(K1 key1){
        Map<K2, V> k2VMap = map.get(key1);
        if (k2VMap==null) return new HashSet<>();
        return k2VMap.keySet();
    }

    public void forEach(Map2Consumer<K1, K2, V> action){
        Objects.requireNonNull(action);
        for (K1 k1 : map.keySet()) {
            Map<K2, V> k2K3VMapMap = map.get(k1);
            for (K2 k2 : k2K3VMapMap.keySet()) {
                V v = k2K3VMapMap.get(k2);
                action.accept(k1,k2,v);
            }
        }
    }



}
