package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class DataImpl implements Data{
    private static DataImpl data;
    private Map<Integer,Meal> storage;
    private int maxDayCalories = 2000;
    private volatile static AtomicInteger count = new AtomicInteger();

    private DataImpl(){
        List<Meal> listOfMeals = MealsUtil.getMeals();
        storage = new ConcurrentHashMap<>();
        listOfMeals.forEach(m -> storage.put(count.incrementAndGet(),m));
    }

    public static synchronized Data getInstance(){
        if(Objects.isNull(data)){
            data = new DataImpl();
        }
        return data;
    }

    public int getMaxDayCalories() {
        return maxDayCalories;
    }

    public void setMaxDayCalories(int maxDayCalories) {
        this.maxDayCalories = maxDayCalories;
    }


    @Override
    public Map<Integer,MealTo> getAll() {
        return MealsUtil.getWithExcess(storage,maxDayCalories);
    }

    @Override
    public void edit(Meal meal,int id) {
        storage.put(id,meal);
    }

    @Override
    public void add(Meal meal) {
        storage.put(count.incrementAndGet(),meal);
    }

    @Override
    public void remove(int id) {
        storage.remove(id);
    }

    @Override
    public Meal getById(int id) {
        return storage.get(id);
    }
}
