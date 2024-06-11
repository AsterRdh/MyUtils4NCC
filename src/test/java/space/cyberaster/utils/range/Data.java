package space.cyberaster.utils.range;

public class Data implements Cloneable {
    int key;
    int index;
    int value;

    public Data(int key,int index, int value) {
        this.key = key;
        this.index = index;
        this.value = value;
    }

    public int getIndex() {
        return index;
    }

    public int getValue() {
        return value;
    }

    @Override
    protected Data clone()  {
        return new Data(key,index,value);
    }
}
