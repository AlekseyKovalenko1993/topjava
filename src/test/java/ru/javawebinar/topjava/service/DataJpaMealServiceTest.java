package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;

@ActiveProfiles("jpa")
public class DataJpaMealServiceTest extends MealServiceTest{

    @Test
    public void getMealAndCheckUser(){
        Meal meal = service.get(MealTestData.MEAL1_ID, UserTestData.USER_ID);
        User user = meal.getUser();
        UserTestData.assertMatch(user,UserTestData.USER);
    }
}
