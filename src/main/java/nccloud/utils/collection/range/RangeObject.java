package nccloud.utils.collection.range;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RangeObject <K,V>{
    public RangeValue<K,V> nowValue = null;
    private int nowPointer = -1;

    private List<RangeValue<K,V>> values = new ArrayList<>();
    private Set<RangeValue<K,V>> removedValues = new HashSet<>();

    public RangeValue<K,V> next(){
        int newPointer = nowPointer + 1;
        if(newPointer >= values.size()){
            this.nowPointer = -1;
            this.nowValue = null;
            return null;
        }
        RangeValue<K, V> kvRangeValue = values.get(newPointer);
        while (removedValues.contains(kvRangeValue)){
            newPointer++;
            if(newPointer >= values.size()){
                this.nowPointer = -1;
                this.nowValue = null;
                return null;
            }
            kvRangeValue = values.get(newPointer);
        }
        nowValue = kvRangeValue;
        nowPointer = newPointer;
        return kvRangeValue;
    }

    public void pass(RangeValue<K,V> value){
        removedValues.add(value);
        if(nowValue == value){
            next();
        }
    }

    public void add(RangeValue<K,V> value){
        values.add(value);
    }

    public void init(){
        if (!values.isEmpty()){
            nowPointer = 0;
            removedValues = new HashSet<>();
            nowValue = values.get(0);
        }else {
            nowPointer = -1;
            removedValues = new HashSet<>();
            nowValue = null;
        }
    }

}
