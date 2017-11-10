package com.java.exception.resolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

public class GolbalException implements HandlerExceptionResolver {

	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {
		ModelAndView modelAndView = new ModelAndView();
		
		modelAndView.setViewName("error/exception");
		modelAndView.addObject("message", "页面	访问	出错	····");
		return modelAndView;
	}

}
