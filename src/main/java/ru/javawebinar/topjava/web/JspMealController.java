package ru.javawebinar.topjava.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalDate;
import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalTime;
import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;

@Controller
@RequestMapping(value = "/meals")
public class JspMealController {

    @Autowired
    MealService service;

    @GetMapping("")
    public String getAll(HttpServletRequest request) {
        if (StringUtils.isEmpty(request.getParameter("filter"))) {
            request.setAttribute("meals", MealsUtil.getWithExcess(service.getAll(SecurityUtil.authUserId()), SecurityUtil.authUserCaloriesPerDay()));
        } else {
            LocalDate startDate = parseLocalDate(request.getParameter("startDate"));
            LocalDate endDate = parseLocalDate(request.getParameter("endDate"));
            LocalTime startTime = parseLocalTime(request.getParameter("startTime"));
            LocalTime endTime = parseLocalTime(request.getParameter("endTime"));
            List<Meal> mealsDateFiltered = service.getBetweenDates(startDate, endDate, SecurityUtil.authUserId());
            request.setAttribute("meals", MealsUtil.getFilteredWithExcess(mealsDateFiltered, SecurityUtil.authUserCaloriesPerDay(), startTime, endTime));
        }
        return "meals";
    }


    @GetMapping("/create")
    public String addMeal(Model model) {
        model.addAttribute(new Meal(null, LocalDateTime.now(), "", 1000));
        model.addAttribute("action","create");
        return "mealForm";
    }

    @GetMapping("/update")
    public String updateMeal(@RequestParam int id, Model model) {
        model.addAttribute("meal", service.get(id, SecurityUtil.authUserId()));
        model.addAttribute("action","update");
        return "mealForm";
    }

    @GetMapping("/delete")
    public String deleteMeal(@RequestParam int id) {
        service.delete(id, SecurityUtil.authUserId());
        return "redirect: http://localhost:8080/topjava/meals";
    }

    @PostMapping("/save")
    public String saveMeal(HttpServletRequest request) throws IOException {
        request.setCharacterEncoding("UTF-8");
        Meal meal = new Meal(LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")));
        if (StringUtils.isEmpty(request.getParameter("id"))) {
            service.create(meal, SecurityUtil.authUserId());
        } else {
            assureIdConsistent(meal, Integer.parseInt(request.getParameter("id")));
            service.update(meal, SecurityUtil.authUserId());
        }
        return "redirect: http://localhost:8080/topjava/meals/";

    }
}

