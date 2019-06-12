package ru.javawebinar.topjava.web;

import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.Data;
import ru.javawebinar.topjava.util.DataImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class MealServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        System.out.println(action);
        Data data = DataImpl.getInstance();
        if ("edit".equals(action)) {
            int id = Integer.parseInt(req.getParameter("id"));
            req.setAttribute("id",id);
            req.getRequestDispatcher("newOrEditMeal.jsp").forward(req, resp);
        }else if("delete".equals(action)){
            int id = Integer.parseInt(req.getParameter("id"));
            data.remove(id);
            resp.sendRedirect("/topjava/meals");
        }else if("add".equals(action)){
            req.getRequestDispatcher("newOrEditMeal.jsp").forward(req, resp);
        }else{
            Map<Integer,MealTo> mapOfMealsTo = data.getAll();
            req.setAttribute("map",mapOfMealsTo);
            req.getRequestDispatcher("meals.jsp").forward(req,resp);
        }
    }
}
