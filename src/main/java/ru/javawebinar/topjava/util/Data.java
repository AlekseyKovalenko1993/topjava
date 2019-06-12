package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;

import java.util.List;
import java.util.Map;

public interface Data {
    Map<Integer, MealTo> getAll();
    void edit(Meal meal, int id);
    void add(Meal meal);
    void remove(int id);
    Meal getById(int id);
}
