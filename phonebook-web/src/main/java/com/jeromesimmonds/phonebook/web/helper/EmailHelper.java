package com.jeromesimmonds.phonebook.web.helper;

import com.jeromesimmonds.phonebook.core.be.User;
import com.jeromesimmonds.phonebook.web.be.Email;

/**
 * @author Jerome Simmonds
 *
 */
public interface EmailHelper {

	public void send(final Email email) throws Exception;

	public void sendForgotPasswordEmail(User user, String token) throws Exception;

	public void sendSignUpEmail(User user, String token) throws Exception;

	public void sendChangeEmail(User user, String token) throws Exception;

	public void sendChangeEmailCancel(User user, String token, String previousEmailAddress) throws Exception;
}
