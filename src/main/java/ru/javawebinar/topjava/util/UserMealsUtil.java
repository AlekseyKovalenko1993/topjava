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
        System.out.println(getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
//        .toLocalDate();
//        .toLocalTime();
    }

    public static List<UserMealWithExceed> getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO return filtered list with correctly exceeded field
        List<UserMealWithExceed> list = new ArrayList<>();
        Map<LocalDate,Integer> map = new HashMap<>();
        for(UserMeal userMeal : mealList){
            map.merge(userMeal.getDate(),userMeal.getCalories(),(a,b) -> a + b);
        }
        for(UserMeal userMeal : mealList){
            if (TimeUtil.isBetween(userMeal.getTime(),startTime,endTime)) {
                if(map.get(userMeal.getDate()) > caloriesPerDay){
                    list.add(new UserMealWithExceed(userMeal,true));
                }else {
                    list.add(new UserMealWithExceed(userMeal,false));
                }
            }
        }
        return list;
    }

    public static List<UserMealWithExceed> getFilteredWithExceededStream(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay){
        List<UserMealWithExceed> list = new ArrayList<>();
        Map<LocalDate,Integer> map = mealList.stream().collect(Collectors.toMap(
                u -> u.getDate(),
                u -> u.getCalories(),
                (a,b) -> a + b
        ));
        mealList.stream().filter(u -> TimeUtil.isBetween(u.getTime(),startTime,endTime))
                .forEach(u -> {
                   if(map.get(u.getDate()) > caloriesPerDay){
                       list.add(new UserMealWithExceed(u,true));
                   }else
                       list.add(new UserMealWithExceed(u,false));
                });
        return list;
    }
}
