package com.jeromesimmonds.phonebook.web.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jeromesimmonds.phonebook.core.be.Contact;
import com.jeromesimmonds.phonebook.core.be.PhoneNumber;
import com.jeromesimmonds.phonebook.core.bo.ContactBO;
import com.jeromesimmonds.phonebook.core.bo.PhoneNumberBO;
import com.jeromesimmonds.phonebook.web.Constants;
import com.jeromesimmonds.phonebook.web.Utils;
import com.jeromesimmonds.phonebook.web.form.ContactForm;
import com.jeromesimmonds.phonebook.web.helper.AWSHelper;
import com.jeromesimmonds.phonebook.web.security.UserDetailsServiceCustom;
import com.jeromesimmonds.phonebook.web.validator.ValidationUtils;

/**
 * @author Jerome Simmonds
 *
 */
@Controller
@RequestMapping("/post")
public class PostController {

	private static final Logger LOGGER = LoggerFactory.getLogger(PostController.class);
	
	@Autowired
	private ContactBO contactBO;
	@Autowired
	private PhoneNumberBO phoneNumberBO;
	@Autowired 
	private UserDetailsServiceCustom userDetailsService;
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private AWSHelper awsHelper;

	@Value("${aws.bucket.avatars}")
	private String bucketAvatars;
	
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView handleRequest(@RequestParam(value=Constants.ID, defaultValue="0") int id) {
		ModelAndView mav = new ModelAndView(Constants.VIEW_POST);
		
		ContactForm form = new ContactForm();
		if (id > 0) {
			Contact c = contactBO.findById(id);
			if (c != null && c.getUser().getId() == userDetailsService.getLoggedInUser().getId()) {
				form.setId(c.getId());
				form.setFirstName(c.getFirstName());
				form.setLastName(c.getLastName());
				form.setEmail(c.getEmail());
				form.setPhoto(c.getAvatar());
				form.setPhoneNumbers(c.getPhoneNumbers());
			}
		}
		
		mav.addObject(Constants.COMMAND, form);		
		return mav;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView submit(@Valid @ModelAttribute(Constants.COMMAND) ContactForm form, BindingResult result, RedirectAttributes redirectAttrs) {
		if (ValidationUtils.isBlank(form.getFirstName()) && ValidationUtils.isBlank(form.getLastName()))
			result.reject("com.jeromesimmonds.phonebook.constraints.contact.name.required");
		if (result.hasErrors()) return new ModelAndView(Constants.VIEW_POST);

		Contact contact = null;
		if (form.getId() > 0) contact = contactBO.findById(form.getId());
		if (contact == null) {
			contact = new Contact();
			contact.setUser(userDetailsService.getLoggedInUser());
		} else if (contact.getUser().getId() != userDetailsService.getLoggedInUser().getId())
			return new ModelAndView(Constants.REDIRECT_HOME);
		
		contact.setFirstName(ValidationUtils.isBlank(form.getFirstName()) ? null : form.getFirstName());
		contact.setLastName(ValidationUtils.isBlank(form.getLastName()) ? null : form.getLastName());
		contact.setEmail(ValidationUtils.isBlank(form.getEmail()) ? null : form.getEmail());
		
		// Photo
		if (form.getPhotoFile() != null && form.getPhotoFile().getSize() > 0 && form.getPhotoFile().getOriginalFilename() != null) {
			String key = Utils.getRandomImageName(form.getPhotoFile().getOriginalFilename());
			try {
				awsHelper.upload(bucketAvatars, key, form.getPhotoFile().getInputStream());
				if (!ValidationUtils.isBlank(form.getPhoto()))
					awsHelper.delete(bucketAvatars, form.getPhoto());
				contact.setAvatar(key);
			} catch (IOException e) {
				LOGGER.error("Error while uploading photo", e);
			}
		}

		contactBO.save(contact);
		
		// Delete existing phone numbers
		phoneNumberBO.deleteForContact(contact);
		
		// Add new phone numbers
		List<PhoneNumber> phoneNumbers = new ArrayList<PhoneNumber>();
		for (PhoneNumber pn : form.getPhoneNumbers()) {
			if (!ValidationUtils.isBlank(pn.getNumber())) {
				pn.setContact(contact);
				phoneNumbers.add(pn);
			}
		}
		if (phoneNumbers.size() > 0)
			phoneNumberBO.save(phoneNumbers);
		
		redirectAttrs.addFlashAttribute(Constants.FLASH_OK, messageSource.getMessage("flash.ok.confirmation", null, LocaleContextHolder.getLocale()));
		return new ModelAndView(Constants.REDIRECT_HOME);
	}
}
