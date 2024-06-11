package space.cyberaster.utils.range;

import java.util.ArrayList;
import java.util.List;

public class Value {
    int from;
    int to;
    int value;
    int sum;
    List<Data> data = new ArrayList<>();

    public Value(int from, int to, int value, int sum) {
        this.from = from;
        this.to = to;
        this.value = value;
        this.sum = sum;
    }

    public Value(int from, int to, int value) {
        this.from = from;
        this.to = to;
        this.value = value;
        this.sum = 0;
    }

    public void add(int i) {
        sum += i;
    }

    public Data add(Data data) {
        sum += data.value;
        if (sum<=value){
            this.data.add(data);
            return null;
        }else {
            Data clone = data.clone();
            int i = sum - value;
            clone.value = i;
            data.value -= i;
            sum = value;
            this.data.add(data);
            return clone;
        }
    }

    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public int getTo() {
        return to;
    }

    public void setTo(int to) {
        this.to = to;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }
}
