package ru.javawebinar.topjava.repository.jpa;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class JpaMealRepository implements MealRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public Meal save(Meal meal, int userId) {
        if(meal.isNew()){
            em.persist(meal);
            return meal;
        }else
            return em.merge(meal);
    }

    @Override
    @Transactional
    public boolean delete(int id, int userId) {
        return em.createQuery("DELETE FROM Meal m WHERE m.id=:id AND m.user.id=:userId")
                .setParameter("id", id)
                .setParameter("userId", userId)
                .executeUpdate() > 0;
    }

    @Override
    public Meal get(int id, int userId) {
        return em.createQuery("SELECT m FROM Meal m WHERE m.id=:id AND m.user.id=:userId",Meal.class)
                .setParameter("id",id)
                .setParameter("userId",userId)
                .getSingleResult();

    }

    @Override
    public List<Meal> getAll(int userId) {
        return em.createQuery("SELECT m FROM Meal m WHERE m.user.id=:userId ORDER BY m.dateTime DESC",Meal.class)
                .setParameter("userId",userId)
                .getResultList();
    }

    @Override
    public List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        return em.createQuery("SELECT m FROM Meal m WHERE m.dateTime>=:startDate AND m.dateTime<=:endDate AND m.user.id=:userId ORDER BY m.dateTime DESC ",Meal.class)
                .setParameter("startDate",startDate)
                .setParameter("endDate",endDate)
                .setParameter("userId",userId)
                .getResultList();
    }
}