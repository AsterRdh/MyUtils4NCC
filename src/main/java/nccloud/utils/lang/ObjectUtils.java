package nccloud.utils.lang;

public class ObjectUtils {
    public static <T> T getNotNullObject(T obj, T defVale){
        return obj==null?defVale:obj;
    }
}
