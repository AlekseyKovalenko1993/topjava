package ru.javawebinar.topjava.model;

import java.time.LocalDateTime;

public class UserMealWithExceed {
    private final LocalDateTime dateTime;

    private final String description;

    private final int calories;

    private final Flag flag;

    public UserMealWithExceed(LocalDateTime dateTime, String description, int calories, Flag flag) {
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
        this.flag = flag;
    }

    public UserMealWithExceed(UserMeal userMeal,Flag flag){
        this.dateTime = userMeal.getDateTime();
        this.description = userMeal.getDescription();
        this.calories = userMeal.getCalories();
        this.flag = flag;
    }

    @Override
    public String toString() {
        return "UserMealWithExceed{" +
                "dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                ", exceed=" + flag +
                '}';
    }
}
