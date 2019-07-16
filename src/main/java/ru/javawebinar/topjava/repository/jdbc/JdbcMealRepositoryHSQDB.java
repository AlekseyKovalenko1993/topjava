package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@Profile("hsqldb")
public class JdbcMealRepositoryHSQDB extends JdbcMealRepositoryPostgres{

    private static final RowMapper<Meal> ROW_MAPPER = new RowMapper<Meal>() {
        @Override
        public Meal mapRow(ResultSet rs, int rowNum) throws SQLException {
            Meal meal = new Meal();
            meal.setId(rs.getInt("id"));
            meal.setCalories(rs.getInt("calories"));
            meal.setDescription(rs.getString("description"));
            meal.setDateTime(rs.getTimestamp("date_time").toLocalDateTime());
            return meal;
        }
    };

    @Override
    public RowMapper<Meal> getRowMapper() {
        return ROW_MAPPER;
    }


    @Override
    public MapSqlParameterSource getParameters(Meal meal, int userId){
        return new MapSqlParameterSource()
                .addValue("id", meal.getId())
                .addValue("description", meal.getDescription())
                .addValue("calories", meal.getCalories())
                .addValue("date_time", Timestamp.valueOf(meal.getDateTime()))
                .addValue("user_id", userId);
    }

    @Override
    public List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        return jdbcTemplate.query(
                "SELECT * FROM meals WHERE user_id=?  AND date_time BETWEEN  ? AND ? ORDER BY date_time DESC",
                getRowMapper(), userId,Timestamp.valueOf(startDate),Timestamp.valueOf(endDate));
    }
}
