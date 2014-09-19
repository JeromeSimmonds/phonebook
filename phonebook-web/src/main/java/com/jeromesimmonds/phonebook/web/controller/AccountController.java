package com.jeromesimmonds.phonebook.web.controller;

import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
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
import com.jeromesimmonds.phonebook.web.form.AccountEmailForm;
import com.jeromesimmonds.phonebook.web.form.AccountPasswordForm;
import com.jeromesimmonds.phonebook.web.form.AccountPersonalInfoForm;
import com.jeromesimmonds.phonebook.web.helper.EmailHelper;
import com.jeromesimmonds.phonebook.web.security.UserDetailsServiceCustom;

/**
 * @author Jerome Simmonds
 *
 */
@Controller
@RequestMapping("/account")
public class AccountController {

	private static final Logger LOGGER = LoggerFactory.getLogger(AccountController.class);
	
	public static final String SECTION_EMAIL = "/email";
	public static final String SECTION_EMAILCHANGED = "/emailChanged";
	public static final String SECTION_PASSWORD = "/password";
	
	@Autowired
    private MessageSource mMessageSource;
	@Autowired
	private UserBO userBO;
	@Autowired
	private UserTokenBO userTokenBO;
	@Autowired
	private EmailHelper emailHelper;
	@Autowired 
	private UserDetailsServiceCustom userDetailsService;
	
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		AccountPersonalInfoForm form = new AccountPersonalInfoForm();
		// SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		// User user = mUserBO.findById(mUserDetailsService.getLoggedInUser().getId()); // no need because only 1 session per user so can not be changed
		form.setUsername(userDetailsService.getLoggedInUser().getUsername());
		ModelAndView mav = new ModelAndView(Constants.VIEW_ACCOUNT);
		mav.addObject(Constants.COMMAND, form);
		return mav;
	}

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView submitPersonalInfo(@Valid @ModelAttribute(Constants.COMMAND) AccountPersonalInfoForm form, BindingResult result, RedirectAttributes pRedirectAttrs) throws Exception {
		ModelAndView mav = new ModelAndView(Constants.VIEW_ACCOUNT);
		if (result.hasErrors())
			return mav;
		
		User user = userBO.findById(userDetailsService.getLoggedInUser().getId());
		user.setUsername(form.getUsername());
		//TODO: check that username is available (in BO)
		userBO.save(user);
		
		user.setPassword(null);
		userDetailsService.setLoggedInUser(user);

		pRedirectAttrs.addFlashAttribute(Constants.FLASH_OK, mMessageSource.getMessage("flash.ok.success", null, LocaleContextHolder.getLocale()));
		return new ModelAndView(Constants.REDIRECT + Constants.VIEW_ACCOUNT);
	}

	// User not authenticated anymore
	@RequestMapping(value = SECTION_EMAILCHANGED, method = RequestMethod.GET)
	public ModelAndView handleRequestEmailChanged(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView(Constants.VIEW_ACCOUNT);
		mav.addObject(Constants.SECTION, SECTION_EMAILCHANGED);
		return mav;
	}
	
	@RequestMapping(value = SECTION_EMAIL, method = RequestMethod.GET)
	public ModelAndView handleRequestChangeEmail(HttpServletRequest request, HttpServletResponse response) throws Exception {
		AccountEmailForm oForm = new AccountEmailForm();
//		User user = mUserBO.findById(mUserDetailsService.getLoggedInUser().getId());
//		oForm.setEmail(user.getEmail());
		ModelAndView mav = new ModelAndView(Constants.VIEW_ACCOUNT);
		mav.addObject(Constants.COMMAND, oForm);
		mav.addObject(Constants.SECTION, SECTION_EMAIL);
		return mav;
	}

	@RequestMapping(value = SECTION_EMAIL, method = RequestMethod.POST)
	public ModelAndView submitEmail(@Valid @ModelAttribute(Constants.COMMAND) AccountEmailForm form, BindingResult result) throws Exception {
		ModelAndView mav = new ModelAndView(Constants.VIEW_ACCOUNT);
		mav.addObject(Constants.SECTION, SECTION_EMAIL);
		
		if (result.hasErrors())
			return mav;

		User user = userBO.findById(userDetailsService.getLoggedInUser().getId());
		String oCurrentEmail = user.getEmail();
		user.setEmail(form.getEmail());
		user.setConfirmed(false);
		userBO.save(user);
		
		String oToken = new RandomGUID(true).toString().replace(Constants.DASH, Constants.EMPTY);
		UserToken oUT = new UserToken();
		oUT.setToken(oToken);
		oUT.setUser(user);
		oUT.setTypeId(UserToken.TYPE_EMAILCONFIRMATION);
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_YEAR, 2);
		oUT.setExpirationTime(c.getTime()); // Valid 2 days
		userTokenBO.save(oUT);

		emailHelper.sendChangeEmail(user, oToken + user.getId());

		// Token to cancel 
		oToken = new RandomGUID(true).toString().replace(Constants.DASH, Constants.EMPTY);
		oUT = new UserToken();
		oUT.setToken(oToken);
		oUT.setUser(user);
		oUT.setEmail(oCurrentEmail);
		oUT.setTypeId(UserToken.TYPE_EMAILCHANGECANCELLATION);
		c.add(Calendar.DAY_OF_YEAR, 3); // + 3 days
		oUT.setExpirationTime(c.getTime()); // Valid 2 days
		userTokenBO.save(oUT);
		
		emailHelper.sendChangeEmailCancel(user, oToken + user.getId(), oCurrentEmail);
		
		// Logging out
		SecurityContextHolder.getContext().setAuthentication(null); 
		
		return new ModelAndView("redirect:/emailChanged");
		//return new ModelAndView("redirect:/logout");
	}
	
	@RequestMapping(value = SECTION_PASSWORD, method = RequestMethod.GET)
	public ModelAndView handleRequestChangePassword(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView(Constants.VIEW_ACCOUNT);
		mav.addObject(Constants.COMMAND, new AccountPasswordForm());
		mav.addObject(Constants.SECTION, SECTION_PASSWORD);
		return mav;
	}
	
	@RequestMapping(value = SECTION_PASSWORD, method = RequestMethod.POST)
	public ModelAndView submitPassword(@Valid @ModelAttribute(Constants.COMMAND) AccountPasswordForm form, BindingResult result, HttpServletRequest request, HttpServletResponse response) throws Exception { // Locale locale
		ModelAndView mav = new ModelAndView(Constants.VIEW_ACCOUNT);
		mav.addObject(Constants.SECTION, SECTION_PASSWORD);
		
		if (result.hasErrors())
			return mav;

		User user = userBO.findById(userDetailsService.getLoggedInUser().getId());
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

		if (!passwordEncoder.matches(form.getPassword(), user.getPassword())) {
			result.addError(new FieldError(Constants.COMMAND, AccountPasswordForm.FIELD_PASSWORD, mMessageSource.getMessage("com.jeromesimmonds.phonebook.constraints.currentpassword.incorrect", null, LocaleContextHolder.getLocale())));
			return mav;
		}
		
		user.setPassword(passwordEncoder.encode(form.getNewPassword()));
		userBO.save(user);
		
		userDetailsService.clearRememberMe(request, response);
		
		return new ModelAndView(Constants.REDIRECT + Constants.VIEW_ACCOUNT + SECTION_PASSWORD + "?success");
	}
}