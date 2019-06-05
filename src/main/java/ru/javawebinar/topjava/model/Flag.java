package ru.javawebinar.topjava.model;

public class Flag {
    private boolean flag;

    public boolean isFlag() {
        return flag;
    }

    public void switchFlag(boolean flag) {
        this.flag = flag;
    }

    @Override
    public String toString() {
        return Boolean.toString(flag);
    }
}
