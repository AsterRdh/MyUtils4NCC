package nccloud.utils.collection.range;


import nccloud.utils.collection.exception.NoValueException;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * 范围Map
 * @param <K>
 * @param <V>
 */
public class RangeMap <K,V>{
    Map<K,RangeObject<K,V>> map = new HashMap<>();

    public RangeMap() {}

    /**
     * 初始化
     */
    public void init(){
        map.values().forEach(RangeObject::init);
    }

    /**
     * 添加对象
     * @param value 对象
     * @param func 构造数据方法
     */
    public void push(V value , Function<V,RangeValue<K,V>> func) {
        RangeValue<K, V> apply = func.apply(value);
        Set<K> keys = apply.getKeys();
        for (K key : keys) {
            RangeObject<K, V> kvRangeObject = map.get(key);
            if (kvRangeObject == null) {
                kvRangeObject = new RangeObject<>();
                map.put(key, kvRangeObject);
            }
            kvRangeObject.add(apply);
        }
    }

    /**
     * 获取对象
     * @param key key
     * @return
     */
    public RangeValue<K,V> get(K key) {
        RangeObject<K, V> rangeObject = map.get(key);
        if (rangeObject == null) {
            return null;
        }
        return rangeObject.nowValue;
    }

    /**
     * 设置跳过该对象
     * @param value 对象
     */
    public void pass(RangeValue<K,V> value) {
        Set<K> keys = value.getKeys();
        for (K key : keys) {
            map.get(key).pass(value);
        }
    }

    /**
     * 使用该对象
     * @param data 对象
     * @param keyGetter 获取Keu方法
     * @param useFunction 使用方法，如果用完应返回data.clone
     * @param <T> 使用的对象类型
     * @throws NoValueException 无法使用异常
     */
    public  <T extends Cloneable> void useValue(T data, Function<T,K> keyGetter, BiFunction<T,V,T> useFunction) throws NoValueException {
        K key = keyGetter.apply(data);
        RangeValue<K, V> rangeValue = get(key);
        if (rangeValue != null) {
            T dataTemp = data;
            while (dataTemp!=null){
                V value = rangeValue.getValue();
                dataTemp = useFunction.apply(dataTemp, value);
                if(dataTemp != null){
                    pass(rangeValue);
                    rangeValue = get(key);
                    if (rangeValue == null) {
                        throw new NoValueException();
                    }
                }
            }
        }else {
            throw new NoValueException();
        }
    }


}
