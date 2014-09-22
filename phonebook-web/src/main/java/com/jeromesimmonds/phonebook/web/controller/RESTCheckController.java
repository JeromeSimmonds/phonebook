package com.jeromesimmonds.phonebook.web.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.jeromesimmonds.phonebook.core.bo.UserBO;
import com.jeromesimmonds.phonebook.web.Constants;
import com.jeromesimmonds.phonebook.web.be.APIResponse;

/**
 * @author Jerome Simmonds
 *
 */
@Controller
@RequestMapping("/R/check")
public class RESTCheckController {

	private static final Logger LOGGER = LoggerFactory.getLogger(RESTCheckController.class);

	@Autowired
	private UserBO userBO;
	
	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<String> handleRequest(
			@RequestParam(value=Constants.EMAIL, required=false) String email, 
			@RequestParam(value=Constants.USERNAME, required=false) String username) {
		
		APIResponse<Map<String, Boolean>> response = new APIResponse<Map<String, Boolean>>();
		Map<String, Boolean> results = new HashMap<String, Boolean>();
		
		// returns true if email already exists
		if (email != null) {
			results.put(Constants.EMAIL, userBO.emailExists(email));
		}

		// returns true if username already exists
		if (username != null) {
			results.put(Constants.USERNAME, userBO.usernameExists(username));
		}

		response.setResult(results);

		return new ResponseEntity<String>(response.getJSON(), HttpStatus.OK);
	}
}
