package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryMealRepositoryImpl.class);
    private Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS.forEach(this::save);
    }

    @Override
    public Meal save(Meal meal) {
        log.info("Save {}",meal);
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            repository.put(meal.getId(),meal);
            return meal;
        }
        if(!repository.containsKey(meal.getId())){
            return null;
        }
        // treat case: update, but absent in storage
        return meal.getUserId().equals(repository.get(meal.getId()).getUserId())
                ? repository.computeIfPresent(meal.getId(),(id, oldMeal) -> meal) : null;
    }

    @Override
    public boolean delete(int id, int userId) {
        log.info("delete {}", id);
        if(!repository.containsKey(id)){
            return false;
        }
        return repository.get(id).getUserId() == userId && repository.remove(id) != null;
    }

    @Override
    public Meal get(int id,int userId) {
        log.info("get {}" ,id);
        if(!repository.containsKey(id)){
            return null;
        }
        return repository.get(id).getUserId() == userId ? repository.get(id) : null;
    }

    @Override
    public Collection<Meal> getAll(int userId) {
        log.info("getAll() {}" ,userId);
        return repository.values().stream()
                .filter(meal -> userId == meal.getUserId())
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public List<Meal> getFiltered(LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime, int userId) {
        log.info("getFiltered {}",userId);
        return getAll(userId).stream()
                .filter(meal -> DateTimeUtil.isBetween(meal.getDate(),startDate,endDate))
                .filter(meal -> DateTimeUtil.isBetween(meal.getTime(),startTime,endTime))
                .collect(Collectors.toList());
    }
}

