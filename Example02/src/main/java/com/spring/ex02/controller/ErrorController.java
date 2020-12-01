package com.spring.ex02.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Handles requests for the application home page.
 */
@Controller
public class ErrorController {
	
	private static final Logger logger = LoggerFactory.getLogger(ErrorController.class);
		
	@RequestMapping(value = {"/error"}, method = RequestMethod.GET)
	public String serverError(){
		logger.info("error");
		return "common/error";
	}
	
}
