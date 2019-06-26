package ru.javawebinar.topjava.repository.jdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Repository
public class JdbcMealRepositoryImpl implements MealRepository {


    private RowMapper<Meal> ROW_MAPPER;

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final SimpleJdbcInsert insertUser;



    @Autowired
    public JdbcMealRepositoryImpl(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.insertUser = new SimpleJdbcInsert(jdbcTemplate).withTableName("meals").usingGeneratedKeyColumns("meal_id");
    }

    @Autowired
    public void setROW_MAPPER(RowMapper<Meal> ROW_MAPPER) {
        this.ROW_MAPPER = ROW_MAPPER;
    }

    @Override
    public Meal save(Meal meal, int userId) {
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("meal_id",meal.getId())
                .addValue("date_time",DateTimeUtil.convertLocalDateTimeToDate(meal.getDateTime()))
                .addValue("description",meal.getDescription())
                .addValue("calories",meal.getCalories())
                .addValue("user_id",userId);
        if(meal.isNew()){
            meal.setId(insertUser.executeAndReturnKey(map).intValue());
            return meal;
        }else
            return namedParameterJdbcTemplate.update("UPDATE meals SET date_time=:date_time,description=:description, " +
                            "calories=:calories WHERE meal_id=:meal_id AND user_id=:user_id",map) > 0 ? meal : null;
    }

    @Override
    public boolean delete(int id, int userId) {
        return jdbcTemplate.update("DELETE FROM meals WHERE meal_id=? AND user_id=?",id,userId) > 0;
    }

    @Override
    public Meal get(int id, int userId) {
          return jdbcTemplate.queryForObject("SELECT * FROM meals WHERE meal_id=? AND user_id=?",ROW_MAPPER,id,userId);
    }

    @Override
    public List<Meal> getAll(int userId) {
        return jdbcTemplate.query("SELECT * FROM meals WHERE user_id=?",ROW_MAPPER,userId);
    }

    @Override
    public List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        Date start = DateTimeUtil.convertLocalDateTimeToDateWithoutTime(startDate);
        Date end = DateTimeUtil.convertLocalDateTimeToDateWithoutTime(endDate);
        return jdbcTemplate.query("SELECT * FROM meals WHERE (date_time BETWEEN ? AND ?) AND user_id=?",ROW_MAPPER,start,end,userId);
    }
}
