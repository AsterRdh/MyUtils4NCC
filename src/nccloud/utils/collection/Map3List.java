package nccloud.utils.collection;

import nccloud.function.Map3Consumer;

import java.util.*;

public class Map3List<K1,K2,K3,V> {
    Map<K1,MapMapList<K2,K3,V>> map=new HashMap<>();

    public void put(K1 k1,K2 k2,K3 k3,V v){
        MapMapList<K2, K3, V> k2K3VMapMap = map.get(k1);
        if (k2K3VMapMap==null) k2K3VMapMap=new MapMapList<>();
        k2K3VMapMap.add(k2,k3,v);
        map.put(k1,k2K3VMapMap);
    }

    public List<V> get(K1 k1,K2 k2,K3 k3){
        MapMapList<K2, K3, V> k2K3VMapMap = map.get(k1);
        if (k2K3VMapMap==null) return null;
        return k2K3VMapMap.getMapList(k2,k3);
    }

    public  MapMapList<K2, K3, V> getK3Map(K1 k1){
        MapMapList<K2, K3, V> k2K3VMapMap = map.get(k1);

        return k2K3VMapMap;
    }

    public  MapMapList<K2, K3, V> getK3MapNotNUll(K1 k1){
        MapMapList<K2, K3, V> k2K3VMapMap = map.get(k1);
        if (k2K3VMapMap==null) {
            k2K3VMapMap = new MapMapList<>();
            map.put(k1,k2K3VMapMap);

        }
        return k2K3VMapMap;
    }
    public  MapList<K3, V> getK2Map(K1 k1,K2 k2){
        MapMapList<K2, K3, V> k2K3VMapMap = map.get(k1);
        if (k2K3VMapMap==null) {
            return null;
        }
        return k2K3VMapMap.getMapList(k2);
    }

    public  MapList<K3, V> getK2MapNotNUll(K1 k1,K2 k2){
        MapMapList<K2, K3, V> k3MapNotNUll = getK3MapNotNUll(k1);
        MapList<K3, V> k2MapNotNull = k3MapNotNUll.getMapListNotNull(k2);
        return k2MapNotNull;
    }

    public Set<K1> getK1Set(){
        return map.keySet();
    }

    public Set<K2> getK2Set(K1 k1){
        MapMapList<K2, K3, V> k2K3VMapMap = map.get(k1);
        if (k2K3VMapMap==null) return new HashSet<>();
        return map.get(k1).getK1Set();
    }

    public void forEach(Map3Consumer<K1,K2,K3,V> action){
        Objects.requireNonNull(action);
        for (K1 k1 : map.keySet()) {
            MapMapList<K2, K3, V> k2K3VMapMap = map.get(k1);
            for (K2 k2 : k2K3VMapMap.getK1Set()) {
                MapList<K3, V> k2Map = k2K3VMapMap.getMapList(k2);
                for (K3 k3 : k2Map.keySet()) {
                    List<V> vs = k2Map.get(k3);
                    for (V value : vs) {
                        action.accept(k1,k2,k3,value);
                    }
                }
            }
        }
    }



}
