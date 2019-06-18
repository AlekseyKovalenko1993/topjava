package ru.javawebinar.topjava.web.meal;

import com.sun.xml.internal.ws.developer.MemberSubmissionAddressing;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.to.MealTo;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.util.ValidationUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.*;

@Controller
public class MealRestController {
    private static final Logger log = LoggerFactory.getLogger(MealRestController.class);

    private final MealService service;

    @Autowired
    public MealRestController(MealService service) {
        this.service = service;
    }

    public List<MealTo> getAll(){
        log.info("getAll");
        return service.getAll(SecurityUtil.authUserId(),SecurityUtil.authUserCaloriesPerDay());
    }

    public void delete(int id){
        log.info("delete {}",id);
        service.delete(id,SecurityUtil.authUserId());
    }

    public Meal get(int id){
        log.info("get {}", id);
        return service.get(id,SecurityUtil.authUserId());
    }

    public Meal create(Meal meal){
        log.info("create {}",meal);
        checkNew(meal);
        meal.setUserId(SecurityUtil.authUserId());
        return service.save(meal);
    }

    public void update(Meal meal,int id){
        log.info("update {}",meal);
        meal.setUserId(SecurityUtil.authUserId());
        assureIdConsistent(meal,id);
        service.save(meal);
    }

    public List<MealTo> getFiltered(String startDateStr, String endDateStr, String startTimeStr, String endTimeStr){
        log.info("getFiltered {}");
        LocalDate startDate = StringUtils.isBlank(startDateStr) ? LocalDate.MIN : LocalDate.parse(startDateStr);
        LocalDate endDate = StringUtils.isBlank(endDateStr) ? LocalDate.MAX : LocalDate.parse(endDateStr);
        LocalTime startTime = StringUtils.isBlank(startTimeStr) ? LocalTime.MIN : LocalTime.parse(startTimeStr);
        LocalTime endTime = StringUtils.isBlank(endTimeStr) ? LocalTime.MAX : LocalTime.parse(endTimeStr);
        return service.getFiltered(startDate,endDate,startTime,endTime,SecurityUtil.authUserId(),SecurityUtil.authUserCaloriesPerDay());
    }
}