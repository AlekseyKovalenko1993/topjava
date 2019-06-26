package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.jdbc.core.RowMapper;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.DateTimeUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

public class MealRowMapper implements RowMapper<Meal> {
    @Override
    public Meal mapRow(ResultSet rs, int rowNum) throws SQLException {
        Meal meal = new Meal();
        meal.setId(rs.getInt("meal_id"));
        Timestamp timestamp = rs.getTimestamp("date_time");
        meal.setDateTime(DateTimeUtil.convertDateToLocalDateTime(timestamp));
        meal.setDescription(rs.getString("description"));
        meal.setCalories(rs.getInt("calories"));
        return meal;
    }
}
