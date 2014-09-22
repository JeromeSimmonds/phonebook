package com.jeromesimmonds.phonebook.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jeromesimmonds.phonebook.core.be.Contact;
import com.jeromesimmonds.phonebook.core.bo.ContactBO;
import com.jeromesimmonds.phonebook.web.Constants;
import com.jeromesimmonds.phonebook.web.helper.AWSHelper;
import com.jeromesimmonds.phonebook.web.security.UserDetailsServiceCustom;
import com.jeromesimmonds.phonebook.web.validator.ValidationUtils;

/**
 * @author Jerome Simmonds
 *
 */
@Controller
@RequestMapping("/delete")
public class DeleteController {

	private static final Logger LOGGER = LoggerFactory.getLogger(DeleteController.class);

	@Autowired
	private ContactBO contactBO;
	@Autowired 
	private UserDetailsServiceCustom userDetailsService;
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private AWSHelper awsHelper;

	@Value("${aws.bucket.avatars}")
	private String bucketAvatars;

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView handleRequest(@RequestParam(value=Constants.ID, required=true) int id, RedirectAttributes redirectAttrs) {
		Contact c = contactBO.findById(id);
		if (c != null && c.getUser().getId() == userDetailsService.getLoggedInUser().getId()) {
			contactBO.delete(c);

			// Deleting possible photo
			if (!ValidationUtils.isBlank(c.getAvatar()))
				awsHelper.delete(bucketAvatars, c.getAvatar());

			// Delete existing phone numbers: no need car db constraint
			//phoneNumberBO.deleteForContact(contact);

			redirectAttrs.addFlashAttribute(Constants.FLASH_OK, messageSource.getMessage("flash.ok.deleted", null, LocaleContextHolder.getLocale()));
		}

		return new ModelAndView(Constants.REDIRECT_HOME);
	}
}
