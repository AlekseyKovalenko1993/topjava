package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Flag;
import java.util.Objects;

public class UtilCounter {
    private int calories;
    private Flag flag;
    private int caloriesPerDay;


    public UtilCounter(int calories,int caloriesPerDay,Flag flag) {
        this.calories = calories;
        this.caloriesPerDay = caloriesPerDay;
        this.flag = flag;
    }

    public Flag getflag() {
        return flag;
    }


    public int getCaloriesPerDay() {
        return calories;
    }

    public void addCalories(int calories){
        this.calories += calories;
        if(this.calories > caloriesPerDay){
            flag.switchFlag(true);
        }
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UtilCounter)) return false;
        UtilCounter that = (UtilCounter) o;
        return calories == that.calories &&
                Objects.equals(flag, that.flag);
    }

    @Override
    public int hashCode() {
        return Objects.hash(flag, calories);
    }
}
