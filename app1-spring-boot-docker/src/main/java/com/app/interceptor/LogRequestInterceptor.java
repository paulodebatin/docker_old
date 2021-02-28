package com.app.interceptor;

import javax.servlet.DispatcherType;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.app.service.LoggingService;

@Component
public class LogRequestInterceptor implements HandlerInterceptor {

	static Logger log = LoggerFactory.getLogger(LogRequestInterceptor.class);
	
	@Autowired
    LoggingService loggingService;
	
	@Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
		

        if (DispatcherType.REQUEST.name().equals(request.getDispatcherType().name()) && request.getMethod().equals(HttpMethod.GET.name())) {
        	loggingService.logRequest(request, null);
        }
			
		System.out.println("Passou");
		return true;
	}
	
}
