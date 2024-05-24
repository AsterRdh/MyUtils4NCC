package nccloud.utils.lang;

import org.apache.commons.lang3.CharSequenceUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class StringUtils {
    public static boolean isBlank(String str){
        return str==null || isBlank3(str);
    }

    public static boolean isNotBlank(String str){
        return !isBlank(str);
    }

    public static String caseToString(Object o){
        if (o==null) return null;
        return o.toString();
    }
    public static <T> T isGetValue(T obj,T defValue){
        return obj==null?defValue:obj;
    }

    public static int compare(String o1,String o2){
        return (isBlank(o1)?"":o1).compareTo((isBlank(o2)?"":o2));
    }

    public static boolean isBlank3(CharSequence cs) {
        int strLen;
        if (cs != null && (strLen = cs.length()) != 0) {
            for(int i = 0; i < strLen; ++i) {
                if (!Character.isWhitespace(cs.charAt(i))) {
                    return false;
                }
            }

            return true;
        } else {
            return true;
        }
    }
    public static boolean isEmpty(CharSequence cs) {
        return cs == null || cs.length() == 0;
    }
    public static boolean isNotEmpty(CharSequence cs) {
        return !isEmpty(cs);
    }


    public static <E> E verifyDate(String value,Class<E> clazz) {
        try {
            Constructor<E> dataConstructor = clazz.getConstructor(String.class);
            E e = dataConstructor.newInstance(value);
            return e;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 依据字符串实际Bytes截取字符串（二分法）
     * @param str 原字符串
     * @param size 目标占用空间（bits）
     * @return
     */
    public static String split(String str, int size){
        int length = str.getBytes().length;
        if (length<=size){
            return str;
        }else {
            int half = str.length() / 2;
            String left = str.substring(0,half);
            String split = split(left, size);
            String right = str.substring(half+1);
            String split2 = split(right, size-split.getBytes().length);
            return split+split2;
        }
    }

    private static StringBuilder newStringBuilder(int noOfItems) {
        return new StringBuilder(noOfItems * 16);
    }

    public static boolean equals(CharSequence cs1, CharSequence cs2) {
        if (cs1 == cs2) {
            return true;
        } else if (cs1 != null && cs2 != null) {
            if (cs1.length() != cs2.length()) {
                return false;
            } else {
                return cs1 instanceof String && cs2 instanceof String ? cs1.equals(cs2) :
                        regionMatches(cs1, false, 0, cs2, 0, cs1.length());
            }
        } else {
            return false;
        }
    }

    @SafeVarargs
    public static <T> String join(T... elements) {
        return join((Object[])elements, (String)null);
    }

    public static String join(Object[] array, char separator) {
        return array == null ? null : join((Object[])array, separator, 0, array.length);
    }

    public static String join(long[] array, char separator) {
        return array == null ? null : join((long[])array, separator, 0, array.length);
    }

    public static String join(int[] array, char separator) {
        return array == null ? null : join((int[])array, separator, 0, array.length);
    }

    public static String join(short[] array, char separator) {
        return array == null ? null : join((short[])array, separator, 0, array.length);
    }

    public static String join(byte[] array, char separator) {
        return array == null ? null : join((byte[])array, separator, 0, array.length);
    }

    public static String join(char[] array, char separator) {
        return array == null ? null : join((char[])array, separator, 0, array.length);
    }

    public static String join(float[] array, char separator) {
        return array == null ? null : join((float[])array, separator, 0, array.length);
    }

    public static String join(double[] array, char separator) {
        return array == null ? null : join((double[])array, separator, 0, array.length);
    }

    public static String join(Object[] array, char separator, int startIndex, int endIndex) {
        if (array == null) {
            return null;
        } else {
            int noOfItems = endIndex - startIndex;
            if (noOfItems <= 0) {
                return "";
            } else {
                StringBuilder buf = newStringBuilder(noOfItems);

                for(int i = startIndex; i < endIndex; ++i) {
                    if (i > startIndex) {
                        buf.append(separator);
                    }

                    if (array[i] != null) {
                        buf.append(array[i]);
                    }
                }

                return buf.toString();
            }
        }
    }

    public static String join(long[] array, char separator, int startIndex, int endIndex) {
        if (array == null) {
            return null;
        } else {
            int noOfItems = endIndex - startIndex;
            if (noOfItems <= 0) {
                return "";
            } else {
                StringBuilder buf = newStringBuilder(noOfItems);

                for(int i = startIndex; i < endIndex; ++i) {
                    if (i > startIndex) {
                        buf.append(separator);
                    }

                    buf.append(array[i]);
                }

                return buf.toString();
            }
        }
    }

    public static String join(int[] array, char separator, int startIndex, int endIndex) {
        if (array == null) {
            return null;
        } else {
            int noOfItems = endIndex - startIndex;
            if (noOfItems <= 0) {
                return "";
            } else {
                StringBuilder buf = newStringBuilder(noOfItems);

                for(int i = startIndex; i < endIndex; ++i) {
                    if (i > startIndex) {
                        buf.append(separator);
                    }

                    buf.append(array[i]);
                }

                return buf.toString();
            }
        }
    }

    public static String join(byte[] array, char separator, int startIndex, int endIndex) {
        if (array == null) {
            return null;
        } else {
            int noOfItems = endIndex - startIndex;
            if (noOfItems <= 0) {
                return "";
            } else {
                StringBuilder buf = newStringBuilder(noOfItems);

                for(int i = startIndex; i < endIndex; ++i) {
                    if (i > startIndex) {
                        buf.append(separator);
                    }

                    buf.append(array[i]);
                }

                return buf.toString();
            }
        }
    }

    public static String join(short[] array, char separator, int startIndex, int endIndex) {
        if (array == null) {
            return null;
        } else {
            int noOfItems = endIndex - startIndex;
            if (noOfItems <= 0) {
                return "";
            } else {
                StringBuilder buf = newStringBuilder(noOfItems);

                for(int i = startIndex; i < endIndex; ++i) {
                    if (i > startIndex) {
                        buf.append(separator);
                    }

                    buf.append(array[i]);
                }

                return buf.toString();
            }
        }
    }

    public static String join(char[] array, char separator, int startIndex, int endIndex) {
        if (array == null) {
            return null;
        } else {
            int noOfItems = endIndex - startIndex;
            if (noOfItems <= 0) {
                return "";
            } else {
                StringBuilder buf = newStringBuilder(noOfItems);

                for(int i = startIndex; i < endIndex; ++i) {
                    if (i > startIndex) {
                        buf.append(separator);
                    }

                    buf.append(array[i]);
                }

                return buf.toString();
            }
        }
    }

    public static String join(double[] array, char separator, int startIndex, int endIndex) {
        if (array == null) {
            return null;
        } else {
            int noOfItems = endIndex - startIndex;
            if (noOfItems <= 0) {
                return "";
            } else {
                StringBuilder buf = newStringBuilder(noOfItems);

                for(int i = startIndex; i < endIndex; ++i) {
                    if (i > startIndex) {
                        buf.append(separator);
                    }

                    buf.append(array[i]);
                }

                return buf.toString();
            }
        }
    }

    public static String join(float[] array, char separator, int startIndex, int endIndex) {
        if (array == null) {
            return null;
        } else {
            int noOfItems = endIndex - startIndex;
            if (noOfItems <= 0) {
                return "";
            } else {
                StringBuilder buf = newStringBuilder(noOfItems);

                for(int i = startIndex; i < endIndex; ++i) {
                    if (i > startIndex) {
                        buf.append(separator);
                    }

                    buf.append(array[i]);
                }

                return buf.toString();
            }
        }
    }

    public static String join(Object[] array, String separator) {
        return array == null ? null : join((Object[])array, separator, 0, array.length);
    }

    public static String join(Object[] array, String separator, int startIndex, int endIndex) {
        if (array == null) {
            return null;
        } else {
            if (separator == null) {
                separator = "";
            }

            int noOfItems = endIndex - startIndex;
            if (noOfItems <= 0) {
                return "";
            } else {
                StringBuilder buf = newStringBuilder(noOfItems);

                for(int i = startIndex; i < endIndex; ++i) {
                    if (i > startIndex) {
                        buf.append(separator);
                    }

                    if (array[i] != null) {
                        buf.append(array[i]);
                    }
                }

                return buf.toString();
            }
        }
    }

    public static String join(Iterator<?> iterator, char separator) {
        if (iterator == null) {
            return null;
        } else if (!iterator.hasNext()) {
            return "";
        } else {
            Object first = iterator.next();
            if (!iterator.hasNext()) {
                return Objects.toString(first, "");
            } else {
                StringBuilder buf = new StringBuilder(256);
                if (first != null) {
                    buf.append(first);
                }

                while(iterator.hasNext()) {
                    buf.append(separator);
                    Object obj = iterator.next();
                    if (obj != null) {
                        buf.append(obj);
                    }
                }

                return buf.toString();
            }
        }
    }

    public static String join(Iterator<?> iterator, String separator) {
        if (iterator == null) {
            return null;
        } else if (!iterator.hasNext()) {
            return "";
        } else {
            Object first = iterator.next();
            if (!iterator.hasNext()) {
                return Objects.toString(first, "");
            } else {
                StringBuilder buf = new StringBuilder(256);
                if (first != null) {
                    buf.append(first);
                }

                while(iterator.hasNext()) {
                    if (separator != null) {
                        buf.append(separator);
                    }

                    Object obj = iterator.next();
                    if (obj != null) {
                        buf.append(obj);
                    }
                }

                return buf.toString();
            }
        }
    }

    public static String join(Iterable<?> iterable, char separator) {
        return iterable == null ? null : join(iterable.iterator(), separator);
    }

    public static String join(Iterable<?> iterable, String separator) {
        return iterable == null ? null : join(iterable.iterator(), separator);
    }

    public static String join(List<?> list, char separator, int startIndex, int endIndex) {
        if (list == null) {
            return null;
        } else {
            int noOfItems = endIndex - startIndex;
            if (noOfItems <= 0) {
                return "";
            } else {
                List<?> subList = list.subList(startIndex, endIndex);
                return join(subList.iterator(), separator);
            }
        }
    }

    public static String join(List<?> list, String separator, int startIndex, int endIndex) {
        if (list == null) {
            return null;
        } else {
            int noOfItems = endIndex - startIndex;
            if (noOfItems <= 0) {
                return "";
            } else {
                List<?> subList = list.subList(startIndex, endIndex);
                return join(subList.iterator(), separator);
            }
        }
    }

    static boolean regionMatches(CharSequence cs, boolean ignoreCase, int thisStart, CharSequence substring, int start, int length) {
        if (cs instanceof String && substring instanceof String) {
            return ((String)cs).regionMatches(ignoreCase, thisStart, (String)substring, start, length);
        } else {
            int index1 = thisStart;
            int index2 = start;
            int tmpLen = length;
            int srcLen = cs.length() - thisStart;
            int otherLen = substring.length() - start;
            if (thisStart >= 0 && start >= 0 && length >= 0) {
                if (srcLen >= length && otherLen >= length) {
                    while(tmpLen-- > 0) {
                        char c1 = cs.charAt(index1++);
                        char c2 = substring.charAt(index2++);
                        if (c1 != c2) {
                            if (!ignoreCase) {
                                return false;
                            }

                            if (Character.toUpperCase(c1) != Character.toUpperCase(c2) && Character.toLowerCase(c1) != Character.toLowerCase(c2)) {
                                return false;
                            }
                        }
                    }

                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }
    }

}
