package ru.javawebinar.topjava.repository.jpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.service.UserService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class JpaMealRepository implements MealRepository {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    UserService userService;

    @Override
    @Transactional
    public Meal save(Meal meal, int userId) {
        User user = userService.get(userId);
        if (meal.isNew()) {
            meal.setUser(user);
            em.persist(meal);
            return meal;
        } else
            return em.createNamedQuery(Meal.UPDATE)
                    .setParameter(1,meal.getDateTime())
                    .setParameter(2,meal.getCalories())
                    .setParameter(3,meal.getDescription())
                    .setParameter(4,meal.getId())
                    .setParameter(5,userId)
                    .executeUpdate() > 0 ? meal : null;
    }

    @Override
    @Transactional
    public boolean delete(int id, int userId) {
        return em.createNamedQuery(Meal.DELETE)
                .setParameter("id", id)
                .setParameter("userId", userId)
                .executeUpdate() > 0;
    }

    @Override
    public Meal get(int id, int userId) {
        return em.createNamedQuery(Meal.GET,Meal.class)
                .setParameter("id",id)
                .setParameter("userId",userId)
                .getSingleResult();

    }

    @Override
    public List<Meal> getAll(int userId) {
        return em.createNamedQuery(Meal.GET_ALL,Meal.class)
                .setParameter("userId",userId)
                .getResultList();
    }

    @Override
    public List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        return em.createNamedQuery(Meal.GET_BETWEEN,Meal.class)
                .setParameter("startDate",startDate)
                .setParameter("endDate",endDate)
                .setParameter("userId",userId)
                .getResultList();
    }
}