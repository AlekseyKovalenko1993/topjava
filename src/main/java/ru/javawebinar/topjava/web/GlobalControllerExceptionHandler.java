package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.util.ValidationUtil;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalControllerExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalControllerExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public ModelAndView defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
        log.error("Exception at request " + req.getRequestURL(), e);
        Throwable rootCause = ValidationUtil.getRootCause(e);
        ModelAndView mav = new ModelAndView("exception/exception");
        mav.addObject("exception", rootCause);
        mav.addObject("message", rootCause.toString());

        // Interceptor is not invoked, put userTo
        AuthorizedUser authorizedUser = SecurityUtil.safeGet();
        if (authorizedUser != null) {
            mav.addObject("userTo", authorizedUser.getUserTo());
        }
        return mav;
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ModelAndView defaultUnique(HttpServletRequest req, Exception e) throws Exception {
        log.error("Exception at request " + req.getRequestURL(), e);
        ModelAndView mav = new ModelAndView("exception/exception");
        Throwable rootCause = ValidationUtil.getRootCause(e);
        if(rootCause.getMessage().contains("email")){
            mav.addObject("exception", rootCause);
            mav.addObject("message", "User with this email already exists");
        }
        return mav;
    }

}
