package ru.javawebinar.topjava.web.meal;


import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import ru.javawebinar.topjava.TestUtil;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.AbstractControllerTest;
import ru.javawebinar.topjava.web.SecurityUtil;
import ru.javawebinar.topjava.web.json.JsonUtil;

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javawebinar.topjava.MealTestData.*;


public class MealRestControllerTest extends AbstractControllerTest {

    private static final String MEAL_REST_URL = MealRestController.MEAL_REST_URL + '/';

    @Test
    void testGet() throws Exception{
        mockMvc.perform(get(MEAL_REST_URL + MEAL1_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(MEAL1));
    }

    @Test
    void testDelete() throws Exception{
        List<Meal> expected = List.of(MEAL6,MEAL5,MEAL4,MEAL3,MEAL2);
        mockMvc.perform(delete(MEAL_REST_URL + MEAL1_ID))
                .andExpect(status().isNoContent())
                .andDo(print());
        assertMatch(mealService.getAll(SecurityUtil.authUserId()),expected);
    }

    @Test
    void testGetAll() throws Exception{
        mockMvc.perform(get(MEAL_REST_URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(MealsUtil.getWithExcess(MEALS,2000).toArray(new MealTo[0])));
    }

    @Test
    void testCreateMeal() throws Exception{
        Meal expected = new Meal(LocalDateTime.now(),"завтрак",1000);
        ResultActions action = mockMvc.perform(post(MEAL_REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(expected)))
                .andExpect(status().isCreated());
        Meal returned = TestUtil.readFromJson(action,Meal.class);
        expected.setId(returned.getId());
        assertMatch(returned,expected);
    }

    @Test
    void testUpdate() throws Exception{
        Meal updated = new Meal(MEAL1);
        updated.setDescription("завтрак");
        mockMvc.perform(put(MEAL_REST_URL + MEAL1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertMatch(mealService.get(MEAL1_ID, UserTestData.USER_ID),updated);
    }

    @Test
    void testGetBetween() throws Exception{
        List<Meal> meals = List.of(MEAL3,MEAL2,MEAL1);
        mockMvc.perform(get(MEAL_REST_URL + "between?startDate=2015-05-30&endDate=2015-05-30"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(MealsUtil.getWithExcess(meals,2000).toArray(new MealTo[0])));
    }
}
