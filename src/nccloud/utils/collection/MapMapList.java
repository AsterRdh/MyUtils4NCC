package nccloud.utils.collection;

import nccloud.function.Map2Consumer;

import java.util.*;
import java.util.function.Function;

public class MapMapList<K1,K2,V> {
    private Map<K1, MapList<K2,V>> mapList;

    public MapMapList() {
        this.mapList = new HashMap<>();
    }

    public<D> MapMapList(Collection<D> object, Function<D,K1> getK1Func,Function<D,K2> getK2Func,Function<D,V> getValueFunc) {
        this.mapList = new HashMap<>();
        for (D d : object) {
            K1 k1 = getK1Func.apply(d);
            K2 k2 = getK2Func.apply(d);
            V value = getValueFunc.apply(d);
            add(k1,k2,value);
        }
    }


    public MapList<K2,V> getMapList(K1 key1){
        return mapList.get(key1);
    }

    public List<V> getMapList(K1 key1,K2 key2){
        MapList<K2, V> mapList = this.mapList.get(key1);
        if (mapList==null) return null;
        return mapList.get(key2);
    }

    public void putMapList(K1 key1,MapList<K2,V> mapList){
        this.mapList.put(key1,mapList);
    }

    public void addMapList(K1 key1,MapList<K2,V> mapList){
        MapList<K2, V> k2VMapList = this.mapList.get(key1);
        if (k2VMapList==null){
            this.mapList.put(key1,mapList);
        }else {
            for (K2 k2 : mapList.keySet()) {
                List<V> vsAdd = mapList.get(k2);
                List<V> vsOld = k2VMapList.get(k2);
                if (vsOld==null){
                    k2VMapList.putList(k2,vsAdd);
                }else {
                    vsOld.addAll(vsAdd);
                }
            }
        }
    }

    public void add(K1 key1,K2 key2,V value){
        MapList<K2, V> k2VMapList = this.mapList.get(key1);
        if (k2VMapList==null){
            k2VMapList = new MapList<>();
            this.mapList.put(key1,k2VMapList);
        }
        k2VMapList.put(key2,value);
    }
    public void addAll(K1 key1,K2 key2,Collection<V> values){
        MapList<K2, V> k2VMapList = this.mapList.get(key1);
        if (k2VMapList==null){
            k2VMapList = new MapList<>();
            this.mapList.put(key1,k2VMapList);
        }
        k2VMapList.addAll(key2,values);
    }

    public void initList(K1 key1,K2 key2){
        MapList<K2, V> k2VMapList = this.mapList.get(key1);
        if (k2VMapList==null){
            k2VMapList = new MapList<>();
            this.mapList.put(key1,k2VMapList);
        }
        k2VMapList.initList(key2);
    }

    public void removeMapList(K1 key1){
        this.mapList.remove(key1);
    }

    public void removeValues(K1 key1,K2 key2){
        MapList<K2, V> k2VMapList = this.mapList.get(key1);
        if (k2VMapList!=null){
            k2VMapList.remove(key2);
        }
    }

    public void removeValue(K1 key1,K2 key2,int index){
        MapList<K2, V> k2VMapList = this.mapList.get(key1);
        if (k2VMapList!=null){
            List<V> vs = k2VMapList.get(key2);
            if (vs!=null){
                vs.remove(index);
            }
        }
    }

    public void removeValue(K1 key1,K2 key2,V value){
        MapList<K2, V> k2VMapList = this.mapList.get(key1);
        if (k2VMapList!=null){
            List<V> vs = k2VMapList.get(key2);
            if (vs!=null){
                vs.remove(value);
            }
        }
    }

    public void forEach(Map2Consumer<K1,K2,V> action){
        Set<K1> k1s = this.mapList.keySet();
        for (K1 k1 : k1s) {
            MapList<K2, V> k2VMapList = this.mapList.get(k1);
            for (K2 k2 : k2VMapList.keySet()) {
                List<V> vs = k2VMapList.get(k2);
                for (V v : vs) {
                    action.accept(k1,k2,v);
                }
            }
        }


    }


    public Set<K1> getK1Set(){
        return this.mapList.keySet();
    }
    public Set<K2> getK2Set(K1 key1){
        MapList<K2, V> k2VMapList = this.mapList.get(key1);
        if (k2VMapList==null) return new HashSet<>();
        return k2VMapList.keySet();
    }

    public boolean isEmpty(){
        return this.mapList.isEmpty();
    }

    public boolean isEmptyMapList(K1 key1){
        MapList<K2, V> k2VMapList = this.mapList.get(key1);
        if (k2VMapList==null) return true;
        return k2VMapList.isEmpty();
    }


    public boolean isEmptyList(K1 key1,K2 key2){
        MapList<K2, V> k2VMapList = this.mapList.get(key1);
        if (k2VMapList==null) return true;
        return k2VMapList.isEmptyList(key2);
    }

    public MapList<K2,V> getMapListNotNull(K1 key1) {
        MapList<K2, V> k2VMapList = this.mapList.get(key1);
        if (k2VMapList==null) {
            k2VMapList=new MapList<>();
            this.mapList.put(key1,k2VMapList);
        }

        return k2VMapList;
    }

    public List<V> getMapListNotNull(K1 key1,K2 key2){
        MapList<K2, V> mapListNotNull = getMapListNotNull(key1);
        List<V> vs = mapListNotNull.get(key2);
        if (vs==null){
            vs = new ArrayList<>();
            mapListNotNull.putList(key2,vs);
        }
        return vs;
    }
}
