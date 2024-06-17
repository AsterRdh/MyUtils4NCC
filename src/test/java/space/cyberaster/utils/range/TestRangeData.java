package space.cyberaster.utils.range;

import space.cyberaster.interfaces.IRange;

public class TestRangeData implements IRange {
    public String name;
    public int value;
    public boolean isFull;

    public TestRangeData(String name, int value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public boolean isFull() {
        return isFull;
    }
}
