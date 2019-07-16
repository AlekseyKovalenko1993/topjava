package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.User;

@ActiveProfiles("datajpa")
public class DataJpaUserServiceTest extends UserServiceTest{

    @Test
    public void getAndCheckMeals(){
        User user = service.get(UserTestData.USER_ID);
        MealTestData.assertMatch(user.getMeals(),MealTestData.MEALS);
    }
}
