package nccloud.utils.lang;

public enum TimeEnum {

    YEAR,MONTH,DAY;

    public static TimeEnum get(int type){
        switch (type){
            case 0: return YEAR;
            case 1: return MONTH;
            case 2: return DAY;
            default: return null;
        }
    }
}
