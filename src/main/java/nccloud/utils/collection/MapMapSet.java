package nccloud.utils.collection;

import nccloud.function.Map2Consumer;

import java.io.Serializable;
import java.util.*;
import java.util.function.Function;

public class MapMapSet<K1,K2 extends Serializable,V extends Serializable> {
    private Map<K1, MapSet<K2,V>> mapSet;

    public MapMapSet() {
        this.mapSet = new HashMap<>();
    }

    public<D> MapMapSet(Collection<D> object, Function<D,K1> getK1Func, Function<D,K2> getK2Func, Function<D,V> getValueFunc) {
        this.mapSet = new HashMap<>();
        for (D d : object) {
            K1 k1 = getK1Func.apply(d);
            K2 k2 = getK2Func.apply(d);
            V value = getValueFunc.apply(d);
            add(k1,k2,value);
        }
    }


    public MapSet<K2,V> getMapSet(K1 key1){
        return mapSet.get(key1);
    }

    public Set<V> getSet(K1 key1,K2 key2){
        MapSet<K2, V> mapSet = this.mapSet.get(key1);
        if (mapSet==null) return null;
        return mapSet.get(key2);
    }

    public void putMapSet(K1 key1,MapSet<K2,V> mapSet){
        this.mapSet.put(key1,mapSet);
    }

    public void addMapSet(K1 key1,MapSet<K2,V> mapSet){
        MapSet<K2, V> k2VMapSet = this.mapSet.get(key1);
        if (k2VMapSet==null){
            this.mapSet.put(key1,mapSet);
        }else {
            for (K2 k2 : mapSet.keySet()) {
                Set<V> vsAdd = mapSet.get(k2);
                Set<V> vsOld = k2VMapSet.get(k2);
                if (vsOld==null){
                    k2VMapSet.addAll(k2,vsAdd);
                }else {
                    vsOld.addAll(vsAdd);
                }
            }
        }
    }

    public void add(K1 key1,K2 key2,V value){
        MapSet<K2, V> k2VMapSet = this.mapSet.get(key1);
        if (k2VMapSet==null){
            k2VMapSet = new MapSet<>();
            this.mapSet.put(key1,k2VMapSet);
        }
        k2VMapSet.put(key2,value);
    }
    public void addAll(K1 key1,K2 key2,Collection<V> values){
        MapSet<K2, V> k2VMapSet = this.mapSet.get(key1);
        if (k2VMapSet==null){
            k2VMapSet = new MapSet<>();
            this.mapSet.put(key1,k2VMapSet);
        }
        k2VMapSet.addAll(key2,values);
    }

    public void removeMapSet(K1 key1){
        this.mapSet.remove(key1);
    }

    public void removeValues(K1 key1,K2 key2){
        MapSet<K2, V> k2VMapSet = this.mapSet.get(key1);
        if (k2VMapSet!=null){
            k2VMapSet.removeSet(key2);
        }
    }

    public void removeValue(K1 key1,K2 key2,V value){
        MapSet<K2, V> k2VMapSet = this.mapSet.get(key1);
        if (k2VMapSet!=null){
            Set<V> vs = k2VMapSet.get(key2);
            if (vs!=null){
                vs.remove(value);
            }
        }
    }


    public void forEach(Map2Consumer<K1,K2,V> action){
        Set<K1> k1s = this.mapSet.keySet();
        for (K1 k1 : k1s) {
            MapSet<K2, V> k2VMapSet = this.mapSet.get(k1);
            for (K2 k2 : k2VMapSet.keySet()) {
                Set<V> vs = k2VMapSet.get(k2);
                for (V v : vs) {
                    action.accept(k1,k2,v);
                }
            }
        }


    }

    public Set<K1> getK1Set(){
        return this.mapSet.keySet();
    }
    public Set<K2> getK2Set(K1 key1){
        MapSet<K2, V> k2VMapSet = this.mapSet.get(key1);
        if (k2VMapSet==null) return new HashSet<>();
        return k2VMapSet.keySet();
    }

    public boolean isEmpty(){
        return this.mapSet.isEmpty();
    }

    public boolean isEmptyMapSet(K1 key1){
        MapSet<K2, V> k2VMapSet = this.mapSet.get(key1);
        if (k2VMapSet==null) return true;
        return k2VMapSet.isEmpty();
    }

    public boolean isEmptyList(K1 key1,K2 key2){
        MapSet<K2, V> k2VMapSet = this.mapSet.get(key1);
        if (k2VMapSet==null) return true;
        return k2VMapSet.isEmptySet(key2);
    }

    public boolean isContains(K1 key1,K2 key2,V value){
        MapSet<K2, V> k2VMapSet = mapSet.get(key1);
        if (k2VMapSet==null) return false;
        Set<V> vs = k2VMapSet.get(key2);
        if (vs==null) return false;
        return vs.contains(value);
    }


    public static <O,K1,K2 extends Serializable,V extends Serializable> MapMapSet<K1,K2,V> build(
            Collection<O> collection,
            Function<O,K1> k1Getter,
            Function<O,K2> k2Getter,
            Function<O,V> valueGetter
    ){
        MapMapSet<K1,K2,V> mapMapSet = new MapMapSet<>();
        if (collection==null || collection.isEmpty()) return mapMapSet;
        for (O o : collection) {
            mapMapSet.add(k1Getter.apply(o),k2Getter.apply(o),valueGetter.apply(o));
        }
        return mapMapSet;
    }

}
