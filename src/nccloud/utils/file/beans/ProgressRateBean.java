package nccloud.utils.file.beans;

public class ProgressRateBean {
    private int now;
    private int total;
    private int a;
    private int b;

    public ProgressRateBean(int now, int total, int yMin, int yMax) {
        this.now = now;
        this.total = total;
        this.b = yMin;
        this.a = yMax-yMin;
    }

    public ProgressRateBean(int total, int a, int b) {
        this.total = total;
        this.a = b-a;
        this.b = a;
    }

    public float getNowRate(){
        if (total==0) return 0;
        return (((float) now/(float) total)*a+b)/100f;
    }
    public void addSept(){
        now++;
    }


}
