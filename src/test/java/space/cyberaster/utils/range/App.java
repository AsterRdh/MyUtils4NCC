package space.cyberaster.utils.range;

import space.cyberaster.exception.range.RangeException;

import java.util.List;
import java.util.Map;

public class App {

    public static void main(String[] args) throws RangeException {
        long start = System.currentTimeMillis();

        RangeMap<TestRangeData,TestRangeValue> rangeMap = new RangeMap<>(
                (rangeData, rangeValue, count) -> {
                    int value = rangeValue.value;
                    int balance = rangeData.value;
                    int balanceNew = balance - value;
                    if (balanceNew < 0){
                        rangeData.isFull = true;
                        TestRangeValue clone = rangeValue.clone();
                        rangeValue.value += balanceNew;
                        clone.name+="-part"+(count+1);
                        clone.value = balanceNew * -1;
                        rangeValue.name += "-part"+count;
                        return clone;
                    }else if (balanceNew == 0){
                        rangeData.isFull = true;
                    }
                    rangeData.value = balanceNew;
                    return null;
                },
                (testRangeData, testRangeValue) -> {
                    System.out.println("range found:"+testRangeValue.name+"->"+testRangeData.name);
                },
                testRangeValue -> {
                    System.out.println("No range found");
                    throw new RangeException("No range found");
                }

        );
        rangeMap.add(1, 1, new TestRangeData("A0",10));
        rangeMap.add(1, 3, new TestRangeData("A1",50));
        rangeMap.add(1, 3, new TestRangeData("A2",20));
        rangeMap.add(4, 5, new TestRangeData("B",40));
        rangeMap.add(1, 5, new TestRangeData("C",100));
        rangeMap.add(2, 4, new TestRangeData("D",30));

        rangeMap.init();

        rangeMap.use(2,new TestRangeValue(2, "2A", 25));
        rangeMap.use(2,new TestRangeValue(2, "2B", 1000));
        rangeMap.use(3,new TestRangeValue(3, "3A", 35));


        Map<TestRangeData, List<TestRangeValue>> done = rangeMap.done();

        long end = System.currentTimeMillis();
        System.out.println("done: "+(end-start)+"ms");
    }

}
