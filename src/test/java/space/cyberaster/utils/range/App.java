package space.cyberaster.utils.range;


import nccloud.utils.collection.range.RangeMap;
import nccloud.utils.collection.range.RangeValue;

import java.util.HashSet;
import java.util.Set;


/**
 * 模拟场景
 * 第一笔预算A为10元，1,2,3,4月可用
 * 第二笔预算B为 5元，1,2月可用
 * 第三笔预算C为 8元，3,4月可用
 * 现 1月有一笔支出Z1 为3元
 *    2月有一笔支出Z2 为10元
 * 要判断Z1 Z2是否够用且将Z1 Z2记录到账单中
 */
public class App {

    public static void main( String[] args ) throws Exception {
        RangeMap<Integer, Value> rangeMap = new RangeMap<>();

        // 第一笔预算value1为5元，1到3月可用
        Value value1 = new Value(1,3,5);
        push(rangeMap,value1);

        // 第二笔预算value2为7元，1到2月可用
        Value value2 = new Value(1,2,7);
        push(rangeMap,value2);

        // 第三笔预算value3为6元，2到3月可用
        Value value3 = new Value(2,3,6);
        push(rangeMap,value3);

        // 第四笔预算value4为10元，1到5月可用
        Value value4 = new Value(1,5,10);
        push(rangeMap,value4);

        //初始化Map
        rangeMap.init();

        // 第一笔支出 data1 ，一月支出3元
        Data data1 = new Data(1,1,3);
        // 第二笔支出 data2 ，一月支出6元
        Data data2 = new Data(2,1,6);
        // 第三笔支出 data3 ，三月支出100元
        Data data3 = new Data(3,3,100);

        //使用第一笔支出，如果不够用会抛出异常
        rangeMap.useValue(
                data1,
                Data::getIndex,
                ((data, value) -> value.add(data))
        );

        //使用第二笔支出，如果不够用会抛出异常
        rangeMap.useValue(
                data2,
                Data::getIndex,
                ((data, value) -> value.add(data))
        );

        //使用第三笔支出，如果不够用会抛出异常
        rangeMap.useValue(
                data3,
                Data::getIndex,
                ((data, value) -> value.add(data))
        );
    }


    /**
     * 构建期间实例
     * @param rangeMap
     * @param value
     */
    public static void push( RangeMap<Integer, Value> rangeMap,Value value){
        rangeMap.push(value,(v)->{
            // 开始月份
            int from = value.getFrom();
            // 结束月份
            int to = value.getTo();

            //可用月集合
            Set<Integer> keys = new HashSet<>();
            for(int i=from;i<=to;i++) {
                keys.add(i);
            }
            return new RangeValue<>(v,keys);
        });
    }
}
