package nccloud.utils.math;

import nc.vo.pub.lang.UFDouble;

import java.math.BigDecimal;

public class MathUtils {

    public static final UFDouble NEGATIVE_ONE_DBL = new UFDouble(-1);

    /**
     * 比例计算 sn*(a/b)
     * @param sn 基数
     * @param a  分子
     * @param b  分母
     * @return 向上取整
     */
    public static int contI(int sn,String a,String b){
        if (b==null) return sn;
        BigDecimal b1=new BigDecimal(sn);
        BigDecimal i1=new BigDecimal(a);
        BigDecimal i2=new BigDecimal(b);

        if  (i1.compareTo(i2)>=0) return sn;

        //BigDecimal divide = (b1.multiply(i1)).divide(i2);
        return (b1.multiply(i1)).divide(i2,BigDecimal.ROUND_UP).intValue();
    }

    /**
     * 反转一个数（*-1）
     * @param d 数
     * @return 数*-1
     */
    public static UFDouble negative(UFDouble d){
        return d.multiply(NEGATIVE_ONE_DBL);
    }

    /**
     * 是否为负数
     * @param d 数
     * @return 是否为负数
     */
    public static boolean isNegative(UFDouble d){
        return d.compareTo(UFDouble.ZERO_DBL) < 0;
    }

    /**
     * 是否为正数
     * @param d 数
     * @return 是否为正数
     */
    public static boolean isPositive (UFDouble d){
        return d.compareTo(UFDouble.ZERO_DBL) > 0;
    }

    /**
     * 是否为零
     * @param d 数
     * @return 是否为零
     */
    public static boolean isZero (UFDouble d){
        return d.compareTo(UFDouble.ZERO_DBL) == 0;
    }


}
