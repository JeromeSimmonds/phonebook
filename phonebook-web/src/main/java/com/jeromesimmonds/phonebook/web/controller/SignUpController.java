package com.jeromesimmonds.phonebook.web.controller;

import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jeromesimmonds.phonebook.core.be.Authority;
import com.jeromesimmonds.phonebook.core.be.User;
import com.jeromesimmonds.phonebook.core.be.UserAuthority;
import com.jeromesimmonds.phonebook.core.be.UserToken;
import com.jeromesimmonds.phonebook.core.bo.UserAuthorityBO;
import com.jeromesimmonds.phonebook.core.bo.UserBO;
import com.jeromesimmonds.phonebook.core.bo.UserTokenBO;
import com.jeromesimmonds.phonebook.web.Constants;
import com.jeromesimmonds.phonebook.web.RandomGUID;
import com.jeromesimmonds.phonebook.web.form.SignUpForm;
import com.jeromesimmonds.phonebook.web.helper.EmailHelper;
import com.jeromesimmonds.phonebook.web.validator.SignUpValidator;

/**
 * @author Jerome Simmonds
 *
 */
@Controller
@RequestMapping("/signup")
public class SignUpController {

	@Autowired
	private UserBO userBO;
	@Autowired
	private UserAuthorityBO userAuthorityBO;
	@Autowired
	private UserTokenBO userTokenBO;
	@Autowired
	private EmailHelper emailHelper;
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private SignUpValidator signUpValidator;

	@InitBinder
	private void initBinder(WebDataBinder binder) {
		binder.setValidator(signUpValidator);
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView(Constants.VIEW_SIGNUP);
		mav.addObject(Constants.COMMAND, new SignUpForm());
		return mav;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView onSubmit(@Valid @ModelAttribute(Constants.COMMAND) SignUpForm form, BindingResult result, RedirectAttributes redirectAttrs) throws Exception {
		if (result.hasErrors()) {
			// log error?
			return new ModelAndView(Constants.VIEW_SIGNUP);
		}

		User user = new User();
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		user.setEmail(form.getEmail());
		user.setPassword(passwordEncoder.encode(form.getPassword()));
		user.setUsername(form.getUsername());
		user = userBO.save(user);
        
		if (user.getId() > 0) {
			UserAuthority ua = new UserAuthority();
			ua.setUser(user);
			ua.setAuthority(new Authority(Authority.ROLE_USER_ID));
			userAuthorityBO.save(ua);
			
			String token = new RandomGUID(true).toString().replace(Constants.DASH, Constants.EMPTY);
			UserToken userToken = new UserToken();
			userToken.setToken(token);
			userToken.setUser(user);
			userToken.setTypeId(UserToken.TYPE_EMAILCONFIRMATION);
			Calendar c = Calendar.getInstance();
			c.add(Calendar.DAY_OF_YEAR, 2);
			userToken.setExpirationTime(c.getTime()); // Valid 2 days
			userTokenBO.save(userToken);

			emailHelper.sendSignUpEmail(user, token + user.getId()); // token suffix is user id
		}
		
		redirectAttrs.addFlashAttribute(Constants.FLASH_OK, messageSource.getMessage("flash.ok.success", null, LocaleContextHolder.getLocale()));
		return new ModelAndView("redirect:/signup");
	}

	// Link from confirmation email
	@RequestMapping(value = "/{token:[a-zA-Z0-9]{32}}{userId:[0-9]+}", method = RequestMethod.GET)
	public ModelAndView handleRequest(@PathVariable String token, @PathVariable int userId, Model model, RedirectAttributes redirectAttrs) throws Exception {
		ModelAndView mav = new ModelAndView(Constants.VIEW_SIGNUP);
		
		UserToken userToken = userTokenBO.load(token);
		if (userToken == null || userToken.getTypeId() != UserToken.TYPE_EMAILCONFIRMATION || userToken.getExpirationTime().before(new Date()) || userToken.getUser().getId() != userId) {
			mav.addObject(Constants.ERRORCODE, "security_signup_token_error");
			return mav;
		}
		
		User user = userBO.findById(userToken.getUser().getId());
		user.setConfirmed(true);
		userBO.save(user);

		userTokenBO.delete(userToken);

		redirectAttrs.addFlashAttribute(Constants.FLASH_OK, messageSource.getMessage("flash.ok.confirmation", null, LocaleContextHolder.getLocale()));
		return new ModelAndView("redirect:/login");
	}
}