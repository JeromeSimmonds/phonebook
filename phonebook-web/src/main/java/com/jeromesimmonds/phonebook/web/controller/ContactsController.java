package com.jeromesimmonds.phonebook.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.jeromesimmonds.phonebook.web.Constants;
import com.jeromesimmonds.phonebook.web.helper.ContactHelper;
import com.jeromesimmonds.phonebook.web.security.UserDetailsServiceCustom;

/**
 * @author Jerome Simmonds
 *
 */
@Controller
@RequestMapping(value={"/", "/contacts"})
public class ContactsController {

	private final int nbPerPage = 10;

	@Autowired
	private UserDetailsServiceCustom userDetailsService;
	@Autowired
	private ContactHelper contactHelper;

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView handleRequest(
			@RequestParam(value="p", defaultValue="1") int page,
			@RequestParam(value="s", required=false) String sort) {
		// No need to check if user logged in, see security.xml

		if (page < 1) page = 1;

		ModelAndView mav = new ModelAndView(Constants.VIEW_CONTACTS);
		mav.addObject(Constants.CONTACTS, contactHelper.getContactsForPage(page, nbPerPage, userDetailsService.getLoggedInUser(), sort));
		mav.addObject(Constants.PAGE, page);
		mav.addObject(Constants.NBPERPAGE, nbPerPage);
		return mav;
	}
}
