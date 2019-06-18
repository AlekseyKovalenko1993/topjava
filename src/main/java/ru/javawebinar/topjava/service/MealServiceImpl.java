package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.to.MealTo;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.ValidationUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class MealServiceImpl implements MealService {

    private final MealRepository repository;

    @Autowired
    public MealServiceImpl(MealRepository repository) {
        this.repository = repository;
    }

    public MealRepository getRepository() {
        return repository;
    }

    @Override
    public Meal save(Meal meal) {
        return ValidationUtil.checkNotFoundWithId(repository.save(meal),meal.getId());
    }


    @Override
    public void delete(int id, int userId) throws NotFoundException {
        ValidationUtil.checkNotFoundWithId(repository.delete(id,userId),id);
    }

    @Override
    public List<MealTo> getAll(int userId,int caloriesPerDay) {
        return MealsUtil.getWithExcess(repository.getAll(userId),caloriesPerDay);
    }

    @Override
    public Meal get(int id, int userId) throws NotFoundException {
        return ValidationUtil.checkNotFoundWithId(repository.get(id,userId),id);
    }

    @Override
    public List<MealTo> getFiltered(LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime, int userId, int calories) {
        List<MealTo> list = MealsUtil.getWithExcess(repository.getFiltered(startDate,endDate,startTime,endTime,userId),calories);
        System.out.println("get" + list);
        return list;
    }
}