package space.cyberaster.utils.range;

import space.cyberaster.exception.range.IsEndException;
import space.cyberaster.exception.range.NotInitException;
import space.cyberaster.exception.range.RangeException;
import space.cyberaster.function.BiFunction3;
import space.cyberaster.function.NotFoundFunction;
import space.cyberaster.interfaces.IRange;
import space.cyberaster.interfaces.RangeCloneable;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class RangeMap<D extends IRange,V extends RangeCloneable<V>> {
    List<RangeVO> ranges = new ArrayList<>();
    Map<Long,RangeMap2> map = null;
    List<Long> index1 = null;

    private boolean isInit = false;
    private boolean isDone = false;


    public void add(long start, long end,D data) {
        ranges.add(new RangeVO(start, end, data));
    }

    public void init() {
        ranges.sort(Comparator.comparing((Function<RangeVO, Long>) i->i.getEnd()-i.getStart())
                .thenComparing(RangeVO::getStart)
                .thenComparing(RangeVO::getEnd)
        );
        map = new HashMap<>();
        for (int i = 0; i < ranges.size(); i++) {
            RangeVO rangeVO = ranges.get(i);
            rangeVO.level = i;
            RangeMap2 longListMap = map.computeIfAbsent(rangeVO.start, k -> new RangeMap2());
            List<RangeVO> rangeVOS = longListMap.get(rangeVO.end);
            rangeVOS.add(rangeVO);
        }
        index1 = new ArrayList<>(map.keySet());
        index1.sort(Comparator.naturalOrder());

        for (Long l : map.keySet()) {
            RangeMap2 map2 = map.get(l);
            map2.indexes.sort(Comparator.naturalOrder());
            map2.map.forEach((k, v) -> v.sort(Comparator.comparing(RangeVO::getLevel)));
        }
        isInit = true;

    }

    BiFunction3<D,V,Integer,V> onUse ;

    BiConsumer<D,V> onFound ;
    NotFoundFunction<V> onNotFound ;


    public RangeMap(BiFunction3<D,V,Integer, V> onUse, BiConsumer<D, V> onFound, NotFoundFunction<V> onNotFound) {
        this.onUse = onUse;
        this.onFound = onFound;
        this.onNotFound = onNotFound;
    }

    private RangeData use(RangeData rangeData) throws RangeException{
        AtomicBoolean found = new AtomicBoolean(false);
        AtomicReference< RangeData> subRange = new AtomicReference<>();
        AtomicReference< RangeVO> foundRange = new AtomicReference<>();

        index1.stream().filter(start -> start <= rangeData.index).findFirst().ifPresent(start->{
            RangeMap2 map2 = map.get(start);
            map2.indexes.stream().filter(end -> end >= rangeData.index).findFirst().ifPresent(end->{
                found.set(true);
                List<RangeVO> rangeVOS = map2.map.get(end);
                RangeVO rangeVO = rangeVOS.get(0);
                foundRange.set(rangeVO);
                rangeVO.usedData.add(rangeData.value);
                V apply = onUse.apply(rangeVO.value, rangeData.value,rangeData.count);
                if (apply!=null){
                    RangeData clone = rangeData.clone(apply);
                    subRange.set(clone);
                }
                if (rangeVO.isFull()) {
                    rangeVOS.remove(rangeVO);
                    if (rangeVOS.isEmpty()){
                        map2.map.remove(end);
                        map2.indexes.remove(end);
                        if (map2.indexes.isEmpty()){
                            map.remove(start);
                            index1.remove(start);
                        }
                    }
                }
            });
        });
        if (!found.get()) {
            onNotFound.accept(rangeData.value);
//            System.out.println("No range found");
        }else {
            onFound.accept(foundRange.get().value,rangeData.value);
//            System.out.println("Range found: "+ rangeData.value.name+"->"+rangeName.get()+": "+oldValue+" - "+rangeData.value.value+" = "+newValue);
        }
        return subRange.get();
    }

    public void use(long index,V v) throws RangeException {
        if (!isInit) throw new NotInitException();
        if (isDone) throw new IsEndException();

        RangeData rangeData = new RangeData(index, v);
        while (rangeData!=null){
            rangeData = use(rangeData);
        }
    }

    public class RangeMap2{
        List<Long> indexes;
        Map<Long,List<RangeVO>> map = new HashMap<>();

        public RangeMap2() {
            indexes = new ArrayList<>();
            map = new HashMap<>();
        }

        public List<RangeVO> get(long end) {
            return map.computeIfAbsent(end, k -> {
                indexes.add(end);
                return new ArrayList<>();
            });

        }
    }

    private class RangeData implements RangeCloneable<RangeData> {
        private V value;
        private long index;
        private int count;

        public RangeData(long index,V value) {
            this.value = value;
            this.index = index;
            this.count = 1;
        }
        private RangeData(long index,V value,int count) {
            this.index = index;
            this.value = value;
            this.count = count;
        }

        @Override
        public RangeData clone()  {
            return new RangeData(index,value.clone(),count+1);
        }

        public RangeData clone(V apply)  {
            return new RangeData(index,apply,count+1);
        }
    }

    private class RangeVO  {
        private long start;
        private long end;
        private D value;
        private List<V> usedData;
        private int level;

        public RangeVO(long start, long end, D value) {
            this.start = start;
            this.end = end;
            this.value = value;
            this.usedData = new ArrayList<>();
            this.level = 0;
        }

        public long getStart() {
            return start;
        }

        public long getEnd() {
            return end;
        }

        public D getValue() {
            return value;
        }

        public boolean isFull() {
            return value.isFull();
        }

        public List<V> getUsedData() {
            return usedData;
        }

        public int getLevel() {
            return level;
        }
    }


    public Map<D,List<V>> done() {
        isDone = true;
        Map<D,List<V>> map = new HashMap<>();
        for (RangeVO range : ranges) {
            D value = range.value;
            List<V> usedData = range.usedData;
            map.put(value,usedData);
        }
        this.ranges = null;
        this.map = null;
        this.index1 = null;

        return map;
    }

}
