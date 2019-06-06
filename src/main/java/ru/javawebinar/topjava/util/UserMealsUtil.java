package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import javax.jws.soap.SOAPBinding;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510)
        );
        getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
    }

    public static List<UserMealWithExceed> getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO return filtered list with correctly exceeded field
        Map<LocalDate, Integer> mapCaloriesByDay = new HashMap<>();
        for (UserMeal userMeal : mealList) {
            mapCaloriesByDay.merge(userMeal.getDate(), userMeal.getCalories(), Integer::sum);
        }
        List<UserMealWithExceed> userMealWithExceedList = new ArrayList<>();
        for (UserMeal userMeal : mealList) {
            if (TimeUtil.isBetween(userMeal.getTime(), startTime, endTime)) {
                userMealWithExceedList.add(new UserMealWithExceed(userMeal, mapCaloriesByDay.get(userMeal.getDate()) > caloriesPerDay));
            }
        }
        return userMealWithExceedList;
    }

    public static List<UserMealWithExceed> getFilteredWithExceededStream(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay){
        Map<LocalDate,Integer> mapCaloriesByDay = mealList.stream()
                .collect(Collectors.groupingBy(UserMeal::getDate,Collectors.summingInt(UserMeal::getCalories)));
        return mealList.stream().filter(u -> TimeUtil.isBetween(u.getTime(),startTime,endTime))
                .map(u -> new UserMealWithExceed(u,mapCaloriesByDay.get(u.getDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }
}
