package nccloud.utils.collection;

import nccloud.utils.collection.exception.NotArrayOrCollectionException;

import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * 数组工具
 */
public class CollectionUtils {

    /**
     * 是否为空集合
     * @param collection 集合
     * @return
     */
    public static boolean isBlank(Collection collection){
        return collection==null || collection.isEmpty();
    }

    /**
     * 是否为空数组
     * @param collection 数组
     * @return
     */
    public static boolean isBlank(Object[] collection){
        return collection==null || collection.length==0;
    }

    /**
     * 是否为空数组(自动识别)，抛出异常
     * @param obj 数组
     * @return
     */
    public static boolean isBlank(Object obj) throws NotArrayOrCollectionException {
        if (obj==null) return true;
        if (obj.getClass().isArray()){
            return isBlank((Object[])obj);
        }else if (obj instanceof Collection ){
            return isBlank((Collection)obj);
        }
        throw new NotArrayOrCollectionException();
    }

    /**
     * 是否为空数组(自动识别)，不抛出异常，默认返回False
     * @param obj 数组
     * @return
     */
    public static boolean isBlankNoException(Object obj)  {
        if (obj==null) return true;
        if (obj.getClass().isArray()){
            return isBlank((Object[])obj);
        }else if (obj instanceof Collection ){
            return isBlank((Collection)obj);
        }
        return false;
    }

    /**
     * 是否为非空集合
     * @param collection 集合
     * @return
     */
    public static boolean isNotBlank(Collection collection){
        return !isBlank(collection);
    }

    /**
     * 是否为非空数组
     * @param collection 数组
     * @return
     */
    public static boolean isNotBlank(Object[] collection){
        return !isBlank(collection);
    }

    /**
     * 是否为非空数组(自动识别)，抛出异常
     * @param obj 数组
     * @return
     */
    public static boolean isNotBlank(Object obj) throws NotArrayOrCollectionException {
        return !isBlank(obj);
    }

    /**
     * 是否为非空数组(自动识别)，不抛出异常，默认返回False
     * @param obj 数组
     * @return
     */
    public static boolean isNotBlankNoException(Object obj) {
        return !isBlankNoException(obj);
    }

    /**
     * 转换为list
     * @param obj 数组或集合
     * @param clazz 目标类型
     * @return list
     * @param <E> 目标类型
     */
    public static <E> List<E> caseObjToList(Object obj,Class< ? extends E> clazz){
        if (obj==null) return new ArrayList<>();
        if (obj.getClass().isArray()){
            return Arrays.asList((E[])obj);
        }else if (obj instanceof Collection ){
            return new ArrayList<>((Collection<? extends E>) obj);
        }
        return null;
    }

    /**
     * 集合转化为string，逗号分割
     * @param collection
     * @return
     */
    public static String caseToString(Collection collection){
        if (isBlank(collection)) return "";
        StringBuffer stringBuffer=new StringBuffer();
        Iterator iterator = collection.iterator();
        while (iterator.hasNext()){
            stringBuffer.append(iterator.next().toString().trim());
            if (iterator.hasNext()){
                stringBuffer.append(",");
            }
        }
        return stringBuffer.toString();
    }

    /**
     * 集合转化为string，自定义分割
     * @param collection
     * @param regex 分隔符
     * @return
     */
    public static String caseToString(Collection collection,String regex){
        if (isBlank(collection)) return "";
        StringBuffer stringBuffer=new StringBuffer();
        Iterator iterator = collection.iterator();
        while (iterator.hasNext()){
            stringBuffer.append(iterator.next().toString().trim());
            if (iterator.hasNext()){
                stringBuffer.append(regex);
            }
        }
        return stringBuffer.toString();
    }

    /**
     * 获取集合大小，如果过为空则返回0
     * @param collection
     * @return
     */
    public static int getSize(Collection collection){
        return collection==null ? 0 : collection.size();
    }

    /**
     * 获取数组大小，如果过为空则返回0
     * @param collection
     * @return
     */
    public static int getSize(Object[] collection){
        return collection==null ? 0 : collection.length;
    }

    /**
     * 切割list
     * @param collection 源列表
     * @param from 从
     * @param to 到
     * @return 如果源列表为空则返回 new ArrayList，如果长度不够则返回的新列表只到最后一个元素
     */
    public static<T> List<T> subList( List<T> collection,int from,int to){
        if (isBlank(collection) || from>=collection.size()){
            return new ArrayList<>();
        }
        int toTemp = Math.min(collection.size(), to);
        List<T> list = new ArrayList<>();
        for (int i = from; i < toTemp; i++) {
            list.add(collection.get(i));
        }
        return list;
    }

    /**
     * 尝试获取元素
     * @param array 数组
     * @param index 期望下标
     * @return 结果，如果不在范围则返回空不抛异常
     * @param <T> 类型
     */
    public static<T> T tryGet(T[] array,int index){
        if (index<0) return null;
        if (array==null || array.length == 0) return null;
        if (array.length <= index){
            return null;
        }
        return array[index];
    }

    /**
     * 尝试获取元素（默认值）
     * @param array 数组
     * @param index 期望下标
     * @param defValue 默认值
     * @return 结果，如果不在范围则返回默认值不抛异常
     * @param <T> 类型
     */
    public static<T> T tryGet(T[] array,int index,T defValue){
        if (index<0) return defValue;
        if (array==null || array.length == 0) return defValue;
        if (array.length <= index){
            return defValue;
        }
        return array[index];
    }

    /**
     * 尝试获取元素，如果不在范围则返回空不抛异常
     * @param list list
     * @param index 期望下标
     * @return 结果，如果不在范围则返回空不抛异常
     * @param <T> 类型
     */
    public static<T> T tryGet(List<T> list,int index){
        if (index<0) return null;
        if (list==null || list.isEmpty()) return null;
        if (list.size() <= index){
            return null;
        }
        return list.get(index);
    }

    /**
     * 尝试获取元素，如果不在范围则返回空不抛异常
     * @param list list
     * @param index 期望下标
     * @param defValue 默认值
     * @return 结果，如果不在范围则返回空不抛异常
     * @param <T> 类型
     */
    public static<T> T tryGet(List<T> list,int index,T defValue){
        if (index<0) return defValue;
        if (list==null || list.isEmpty()) return defValue;
        if (list.size() <= index){
            return defValue;
        }
        return list.get(index);
    }
//    public static<T> T tryGet(Collection<T> collection,int index){
//        if (index<0) return null;
//        if (collection.size()<index){
//            return null;
//        }
//        Iterator<T> iterator = collection.iterator();
//        int ac = 0;
//        while (iterator.hasNext() && ac<index){
//            T next = iterator.next();
//            if (index==ac){
//                return next;
//            }
//            ac++;
//        }
//        return list.get(index);
//    }


    /**
     * 按照规则合并两个List
     * @param list1 List1
     * @param list2 List2
     * @param keyMapper 唯一依据
     * @param mergeFunction 如果重复处理方法
     * @return 合并后的
     * @param <T> List类型
     * @param <K> 唯一依据类型
     */
    public static<T,K> List<T> marge(List<T> list1, List<T> list2, Function<? super T, ? extends K> keyMapper, BinaryOperator<T> mergeFunction){
        Map<K, T> collect = list1.stream().collect(Collectors.toMap(keyMapper, i -> i));
        for (T t : list2) {
            K key = keyMapper.apply(t);
            T t1 = collect.get(key);
            if (t1 ==null){
                collect.put(key,t);
            }else {
                collect.put(key,mergeFunction.apply(t1,t));
            }
        }
        return new ArrayList<>(collect.values());
    }

    /**
     * 按照规则合并两个List,并转换为Map
     * @param list1 List1
     * @param list2 List2
     * @param keyMapper 唯一依据
     * @param mergeFunction 如果重复处理方法
     * @return 合并后的
     * @param <T> List类型
     * @param <K> 唯一依据类型
     */
    public static<T,K> Map<K, T> margeToMap(List<T> list1, List<T> list2, Function<? super T, ? extends K> keyMapper, BinaryOperator<T> mergeFunction){
        Map<K, T> collect = list1.stream().collect(Collectors.toMap(keyMapper, i -> i));
        for (T t : list2) {
            K key = keyMapper.apply(t);
            T t1 = collect.get(key);
            if (t1 ==null){
                collect.put(key,t);
            }else {
                collect.put(key,mergeFunction.apply(t1,t));
            }
        }
        return collect;
    }



}
