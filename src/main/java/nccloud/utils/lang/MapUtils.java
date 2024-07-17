package nccloud.utils.lang;

import java.util.Map;
import java.util.function.Function;

public class MapUtils {

    public static  <K,V> V getValue(Map<K,Object> map, String key, Function<Object,V> mapper, V defValue){
        if(map==null || map.isEmpty() || key==null){
            return defValue;
        }
        Object value = map.get(key);
        return value == null ? defValue : mapper.apply(value);
    }

    public static <K,V> V getValue(Map<K,Object> map, String key, Function<Object,V> mapper){
        return getValue(map,key,mapper,null);
    }

    public static <K,V> V getValue(Map<K,Object> map,String key){
        return getValue(map,key,(o)->(V)o,null);
    }

    public static <K,V> V getValue(Map<K,Object> map,String key, V defValue){
        return getValue(map,key,(o)->(V)o,defValue);
    }


}
