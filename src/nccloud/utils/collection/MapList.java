package nccloud.utils.collection;
import nccloud.framework.core.exception.ExceptionUtils;
import java.io.Serializable;
import java.util.*;

public class MapList <K, V> implements Serializable, Cloneable {
    private static final long serialVersionUID = -4970977770408732801L;
    private Map<K, List<V>> map = new HashMap();

    public MapList() {
    }

    public void clear() {
        this.map.clear();
    }

    public MapList<K, V> clone() {
        MapList obj = null;

        try {
            obj = (MapList) super.clone();
        } catch (CloneNotSupportedException var7) {
            ExceptionUtils.wrapException(var7);
            return null;
        }

        obj.map = new HashMap();
        Set<Map.Entry<K, List<V>>> entrySet = this.entrySet();
        for (Map.Entry<K, List<V>> entry : entrySet) {
            List<V> value = new ArrayList();
            value.addAll((Collection)entry.getValue());
            K key = entry.getKey();
            obj.map.put(key, value);
        }
        return obj;
    }

    public boolean containsKey(K key) {
        return this.map.containsKey(key);
    }

    public Set<Map.Entry<K, List<V>>> entrySet() {
        return this.map.entrySet();
    }

    public List<V> get(K key) {
        return this.map.get(key);
    }

    public Set<K> keySet() {
        return this.map.keySet();
    }

    public void put(K key, V value) {
        List<V> list = (List)this.map.get(key);
        if (list == null) {
            list = new ArrayList();
        }

        ((List)list).add(value);
        this.map.put(key, list);
    }

    public void putList(K key, Collection<V>  value){
        List<V> set = new ArrayList<>(value);
        this.map.put(key, set);
    }

    public void initList(K key1){
        map.put(key1, new ArrayList());
    }

    public void addAll(K key, Collection<V>  value){
        List<V> set = this.map.get(key);
        if (set == null) {
            set = new ArrayList<>();
        }
        set.addAll(value);
        this.map.put(key, set);
    }

    public void putAll(MapList<K, V> collection) {
        this.map.putAll(collection.toMap());
    }

    public void remove(K key) {
        this.map.remove(key);
    }

    public void remove(K key, V value) {
        List<V> vs = this.map.get(key);
        if (vs!=null){
            vs.remove(value);
        }
    }
    public void removeByIndex(K key, int index) {
        List<V> vs = this.map.get(key);
        if (vs!=null){
            vs.remove(index);
        }
    }

    public int size() {
        return this.map.size();
    }

    public boolean isEmpty(){
        return this.map.isEmpty();
    }

    public boolean isEmptyList(K key){
        List<V> vs = this.map.get(key);
        if (vs==null) return true;
        return vs.isEmpty();

    }

    public Map<K, List<V>> toMap() {
        return this.map;
    }

    public void marge(MapList<K,V> mapList){
        for (K key : mapList.keySet()) {
            List<V> vs = mapList.get(key);
            addAll(key,vs);
        }
    }
}
