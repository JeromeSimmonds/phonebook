package com.jeromesimmonds.phonebook.web.controller;

import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Jerome Simmonds
 *
 */
@Controller
@RequestMapping("/ping")
public class PingController {

	public ModelAndView ping(HttpServletResponse pResponse) throws Exception {
		pResponse.getWriter().println(new Date());
		return null;
	}
}
