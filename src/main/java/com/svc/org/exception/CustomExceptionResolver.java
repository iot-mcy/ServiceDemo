package com.svc.org.exception;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.logging.Logger;

public class CustomExceptionResolver implements HandlerExceptionResolver {

    private Logger logger = Logger.getLogger(CustomExceptionResolver.class.getSimpleName());

    private CustomException exception = null;

    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {

        if (e instanceof CustomException) {
            exception = (CustomException) e;
            logger.info(exception.getMsg());
        } else {
            exception = new CustomException("未知错误");
        }

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("message", exception.getMsg());
        modelAndView.setViewName("error");
        return modelAndView;
    }
}
