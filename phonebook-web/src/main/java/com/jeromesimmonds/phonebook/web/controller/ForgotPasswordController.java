package com.jeromesimmonds.phonebook.web.controller;

import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jeromesimmonds.phonebook.core.be.User;
import com.jeromesimmonds.phonebook.core.be.UserToken;
import com.jeromesimmonds.phonebook.core.bo.UserBO;
import com.jeromesimmonds.phonebook.core.bo.UserTokenBO;
import com.jeromesimmonds.phonebook.web.Constants;
import com.jeromesimmonds.phonebook.web.RandomGUID;
import com.jeromesimmonds.phonebook.web.form.ForgotPasswordEmailForm;
import com.jeromesimmonds.phonebook.web.form.ForgotPasswordNewPasswordForm;
import com.jeromesimmonds.phonebook.web.helper.EmailHelper;
import com.jeromesimmonds.phonebook.web.security.UserDetailsServiceCustom;

/**
 * @author Jerome Simmonds
 *
 */
@Controller
@RequestMapping("/forgotPassword")
public class ForgotPasswordController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ForgotPasswordController.class);
	
	@Autowired
	private UserBO userBO;
	@Autowired
	private UserTokenBO userTokenBO;
	@Autowired
	private EmailHelper emailHelper;
	@Autowired 
	private UserDetailsServiceCustom userDetailsService;
	@Autowired
	private MessageSource messageSource;
	
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView(Constants.VIEW_FORGOTPASSWORD);
		mav.addObject(Constants.COMMAND, new ForgotPasswordEmailForm());
		return mav;
	}

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView onSubmitEmail(@Valid @ModelAttribute(Constants.COMMAND) ForgotPasswordEmailForm form, BindingResult result, RedirectAttributes redirectAttrs) throws Exception {
		if (result.hasErrors()) {
			// log error?
			return new ModelAndView(Constants.VIEW_FORGOTPASSWORD);
		}

		User user = userBO.findByEmail(form.getEmail());
		
		if (user != null) {
			if (user.isDisabled()) {
				ModelAndView mav = new ModelAndView(Constants.VIEW_FORGOTPASSWORD);
				mav.addObject(Constants.ERRORCODE, "forgotPassword.error.accountDisabled");
				return mav;
			}
			
			String token = new RandomGUID(true).toString().replace(Constants.DASH, Constants.EMPTY);
			UserToken ut = new UserToken();
			ut.setToken(token);
			ut.setUser(user);
			ut.setTypeId(UserToken.TYPE_FORGOTPASSWORD);
			Calendar c = Calendar.getInstance();
			c.add(Calendar.DAY_OF_YEAR, 1);
			ut.setExpirationTime(c.getTime()); // Valid 24 hours
			userTokenBO.save(ut);
			
			emailHelper.sendForgotPasswordEmail(user, token + user.getId());
		}
		
		// returning success even if user doesn't exist to prevent hackers from testing existing accounts
		redirectAttrs.addFlashAttribute(Constants.FLASH_OK, messageSource.getMessage("flash.ok.sent", null, LocaleContextHolder.getLocale()));
		return new ModelAndView("redirect:/forgotPassword");
	}
	
	// Link from email: token + user id
	@RequestMapping(value = "/{token:[a-zA-Z0-9]{32}}{userId:[0-9]+}", method = RequestMethod.GET)
	public ModelAndView handleRequest(@PathVariable String token, @PathVariable int userId, Model pModel) throws Exception {
		LOGGER.debug("Link from email for user #{}", userId);
		ModelAndView mav = new ModelAndView(Constants.VIEW_FORGOTPASSWORD);
		
		UserToken ut = userTokenBO.load(token);
		if (ut == null || ut.getTypeId() != UserToken.TYPE_FORGOTPASSWORD || ut.getExpirationTime().before(new Date()) || ut.getUser().getId() != userId) {
			mav.addObject(Constants.ERRORCODE, "forgotPassword.token.error");
			return mav;
		}
		
		mav.addObject("token", token + userId);
		mav.addObject(Constants.COMMAND, new ForgotPasswordNewPasswordForm());
		return mav;
	}
	
	// Submitting new password
	@RequestMapping(value = "/{token:[a-zA-Z0-9]{32}}{userId:[0-9]+}", method = RequestMethod.POST)
	public ModelAndView onSubmitNewPassword(@PathVariable String token, @PathVariable int userId, @Valid @ModelAttribute(Constants.COMMAND) ForgotPasswordNewPasswordForm form, BindingResult result, HttpServletRequest request, HttpServletResponse pResponse) throws Exception {
		ModelAndView mav = new ModelAndView(Constants.VIEW_FORGOTPASSWORD);
		mav.addObject("token", token + userId);
		
		UserToken ut = userTokenBO.load(token);
		if (ut == null || ut.getTypeId() != UserToken.TYPE_FORGOTPASSWORD || ut.getExpirationTime().before(new Date()) || ut.getUser().getId() != userId) {
			mav.addObject(Constants.ERRORCODE, "forgotPassword.token.error");
			return mav;
		}
		
		if (result.hasErrors()) {
			return mav;
		}

		// TODO: should we ask email address from form to be 200% sure? -> now passing user id in token
		
		User user = ut.getUser();
		LOGGER.debug("Changing password for user #{}", user.getEmail());
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		user.setPassword(passwordEncoder.encode(form.getPassword()));
		user.setConfirmed(true); // in case user didn't confirm yet
		userBO.save(user);
		
		userTokenBO.delete(ut);
		
		// TODO: delete all "forgot password" tokens for that user? -> CRON?

		// Auto login?
		userDetailsService.logIn(user.getEmail(), request, pResponse);

		// Not reached because login redirects
		//return new ModelAndView("redirect:/forgotPassword?success");
		return null;
	}
}