package nccloud.utils.collection;

import java.util.HashMap;
import java.util.Map;

public class Counter<E> {
    private Map<E,Integer> map;

    public Counter() {
        this.map = new HashMap<>();
    }

    public int add(E key){
        Integer integer = map.get(key);
        integer = integer==null? 1:integer+1;
        map.put(key,integer);
        return integer;
    }

    public int sub(E key){
        Integer integer = map.get(key);
        integer = integer == null ? 0 : integer - 1;
        integer = integer < 0 ? 0 : integer;
        map.put(key,integer);
        return integer;
    }

    private void push(E key,int count){
        map.put(key,count);

    }

    public int get(E key){
        Integer integer = map.get(key);
        return integer == null ? 0 : integer;
    }

}
