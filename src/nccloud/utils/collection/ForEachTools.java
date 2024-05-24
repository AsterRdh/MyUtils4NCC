package nccloud.utils.collection;

import nccloud.utils.functions.ConsumerWithException;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

public class ForEachTools {
    private static ForEachTools forEachTools;


    private ForEachTools(){

    }

    public static ForEachTools getForEachTools(){
        if (forEachTools==null){
            forEachTools = new ForEachTools();
        }
        return forEachTools;
    }


    public <E,EX extends Exception> void forEach(Collection<E> collection, ConsumerWithException<E,EX> inLoop, ConsumerWithException<E,EX> ifHasNext) throws EX{
        Iterator<E> iterator = collection.iterator();
        while (iterator.hasNext()){
            E next = iterator.next();
            inLoop.accept(next);
            if (iterator.hasNext()){
                ifHasNext.accept(next);
            }
        }
    }

    public <E,EX extends Exception> void forEach(E[] collection, ConsumerWithException<E,EX> inLoop, ConsumerWithException<E,EX> ifHasNext) throws EX{
        Iterator<E> iterator = Arrays.stream(collection).iterator();
        while (iterator.hasNext()){
            E next = iterator.next();
            inLoop.accept(next);
            if (iterator.hasNext()){
                ifHasNext.accept(next);
            }
        }
    }

//    public <K,V> void forEachMap(Map<K,V> map, Consumer<E> inKeyLoop, Consumer<E> ifHasNext){
//
//
//    }
}
