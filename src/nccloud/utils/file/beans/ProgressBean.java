package nccloud.utils.file.beans;

import nccloud.utils.file.enums.ProgressState;

import java.io.Serializable;

public class ProgressBean implements Serializable {

    private ProgressState state;

    private String nowStep;
    private float rate;
    private String key;
    private boolean ok;

    public ProgressBean(ProgressState state, String nowStep, String key, float rate, boolean ok) {
        this.state = state;
        this.nowStep = nowStep;
        this.rate = rate;
        this.key = key;
        this.ok = ok;
    }

    public ProgressBean(String nowStep, String key, float rate) {
        this.nowStep = nowStep;
        this.rate = rate;
        this.key = key;
        this.ok = false;
    }

    public ProgressBean(String nowStep, String key, float rate, boolean ok) {
        this.nowStep = nowStep;
        this.rate = rate;
        this.key = key;
        this.ok = ok;
    }

    public boolean isOk() {
        return ok;
    }

    public ProgressState getState() {
        return state;
    }

    public void setState(ProgressState state) {
        this.state = state;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public String getNowStep() {
        return nowStep;
    }

    public void setNowStep(String nowStep) {
        this.nowStep = nowStep;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
