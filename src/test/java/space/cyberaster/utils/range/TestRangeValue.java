package space.cyberaster.utils.range;

import space.cyberaster.interfaces.RangeCloneable;

public class TestRangeValue implements RangeCloneable<TestRangeValue> {
    public long time;
    public int value;
    public String name;

    public TestRangeValue(long time, String name,int value) {
        this.time = time;
        this.value = value;
        this.name = name;
    }

    @Override
    public TestRangeValue clone(){
        return new TestRangeValue(time,name,value);
    }
}
