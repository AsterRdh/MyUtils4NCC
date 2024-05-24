package nccloud.utils.collection;

import java.util.HashSet;
import java.util.Set;

public class MapSetMap<K1,K2,V> extends MapMap<K1,K2,Set<V>>{

    public void putSet(K1 key,K2 key2,V value){
        Set<V> set = get(key, key2);
        if (set==null) set=new HashSet<>();
        set.add(value);
        put(key,key2,set);
    }

}
