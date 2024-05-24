package nccloud.utils.collection;

import nccloud.function.Map3Consumer;

import java.util.*;

public class Map3<K1,K2,K3,V> {
    Map<K1,MapMap<K2,K3,V>> map=new HashMap<>();

    public void put(K1 k1,K2 k2,K3 k3,V v){
        MapMap<K2, K3, V> k2K3VMapMap = map.get(k1);
        if (k2K3VMapMap==null) k2K3VMapMap=new MapMap<>();
        k2K3VMapMap.put(k2,k3,v);
        map.put(k1,k2K3VMapMap);
    }

    public V get(K1 k1,K2 k2,K3 k3){
        MapMap<K2, K3, V> k2K3VMapMap = map.get(k1);
        if (k2K3VMapMap==null) return null;
        return k2K3VMapMap.get(k2,k3);
    }

    public  MapMap<K2, K3, V> getK3Map(K1 k1){
        MapMap<K2, K3, V> k2K3VMapMap = map.get(k1);

        return k2K3VMapMap;
    }

    public  MapMap<K2, K3, V> getK3MapNotNUll(K1 k1){
        MapMap<K2, K3, V> k2K3VMapMap = map.get(k1);
        if (k2K3VMapMap==null) {
            k2K3VMapMap = new MapMap<>();
            map.put(k1,k2K3VMapMap);

        }
        return k2K3VMapMap;
    }
    public  Map<K3, V> getK2Map(K1 k1,K2 k2){
        MapMap<K2, K3, V> k2K3VMapMap = map.get(k1);
        if (k2K3VMapMap==null) {
            return null;
        }
        return k2K3VMapMap.getK2Map(k2);
    }

    public  Map<K3, V> getK2MapNotNUll(K1 k1,K2 k2){
        MapMap<K2, K3, V> k3MapNotNUll = getK3MapNotNUll(k1);
        Map<K3, V> k2MapNotNull = k3MapNotNUll.getK2MapNotNull(k2);
        return k2MapNotNull;
    }

    public Set<K1> getK1Set(){
        return map.keySet();
    }

    public Set<K2> getK2Set(K1 k1){
        MapMap<K2, K3, V> k2K3VMapMap = map.get(k1);
        if (k2K3VMapMap==null) return new HashSet<>();
        return map.get(k1).getK1Set();
    }

    public void forEach(Map3Consumer<K1,K2,K3,V> action){
        Objects.requireNonNull(action);
        for (K1 k1 : map.keySet()) {
            MapMap<K2, K3, V> k2K3VMapMap = map.get(k1);
            for (K2 k2 : k2K3VMapMap.getK1Set()) {
                Map<K3, V> k2Map = k2K3VMapMap.getK2Map(k2);
                for (K3 k3 : k2Map.keySet()) {
                    V value = k2Map.get(k3);
                    action.accept(k1,k2,k3,value);
                }
            }
        }
    }



}
