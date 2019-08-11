package com.nhq.berry.prm391x_alarmclock_fx01694;

public class Item {
    boolean isOn;
    String time;
    boolean isAM;

    public Item(boolean isOn, String time, boolean isAM) {
        this.isOn = isOn;
        this.time = time;
        this.isAM = isAM;
    }

    public boolean isOn() {
        return isOn;
    }

    public void setOn(boolean on) {
        isOn = on;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isAM() {
        return isAM;
    }

    public void setAM(boolean AM) {
        isAM = AM;
    }
}
