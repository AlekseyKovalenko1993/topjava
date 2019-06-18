package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.to.MealTo;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface MealService {
    Meal save(Meal meal);

    void delete(int id, int userId) throws NotFoundException;

    List<MealTo> getAll(int userId,int caloriesPerDay);

    Meal get(int id,int userId) throws NotFoundException;

    List<MealTo> getFiltered(LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime,int userId,int calories);

}