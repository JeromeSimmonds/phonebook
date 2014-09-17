package com.jeromesimmonds.phonebook.core.bo;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.math.BigInteger;

import org.easymock.Capture;
import org.easymock.EasyMock;
import org.easymock.EasyMockRunner;
import org.easymock.IAnswer;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.jpa.domain.Specification;

import com.jeromesimmonds.phonebook.core.be.FindFilterType;
import com.jeromesimmonds.phonebook.core.be.FindParameters;
import com.jeromesimmonds.phonebook.core.be.User;
import com.jeromesimmonds.phonebook.core.dao.TestData;
import com.jeromesimmonds.phonebook.core.dao.UserDAO;
import com.jeromesimmonds.phonebook.core.dao.UserFindSpecification;

/**
 * @author Jerome Simmonds
 *
 */
@RunWith(EasyMockRunner.class)
public class UserBOTest {

	@Mock
	private UserDAO dao;
	
	@TestSubject
	private final UserBOImpl bo = new UserBOImpl();

	@Test
	public void findById() {
		final Capture<UserFindSpecification> arg = new Capture<UserFindSpecification>();
		expect(dao.findOne(EasyMock.capture(arg))).andReturn(TestData.USER_1);
		replay(dao);
		User actual = bo.findById(TestData.USER_1.getId());
		verify(dao);
		assertNotNull(actual);
		assertEquals(TestData.USER_1.getId(), actual.getId());
		assertEquals(1, arg.getValue().getParameters().getFilters().size());
		assertEquals(FindFilterType.User, arg.getValue().getParameters().getFilters().get(0).getType());
		assertEquals(TestData.USER_1.getId(), arg.getValue().getParameters().getFilters().get(0).getValue());
	}

	@Test
	public void findByUsername() {
		expect(dao.findByUsername(TestData.USER_1.getUsername())).andReturn(TestData.USER_1);
		replay(dao);
		User actual = bo.findByUsername(TestData.USER_1.getUsername());
		verify(dao);
		assertNotNull(actual);
		assertEquals(TestData.USER_1.getUsername(), actual.getUsername());
	}

	@Test
	public void findByEmail() {
		expect(dao.findByEmail(TestData.USER_1.getEmail())).andReturn(TestData.USER_1);
		replay(dao);
		User actual = bo.findByEmail(TestData.USER_1.getEmail());
		verify(dao);
		assertNotNull(actual);
		assertEquals(TestData.USER_1.getEmail(), actual.getEmail());
	}
	
	@Test
	public void usernameExists() {
		expect(dao.usernameExists(TestData.USER_1.getUsername())).andReturn(BigInteger.valueOf(TestData.USER_1.getId()));
		replay(dao);
		boolean actual = bo.usernameExists(TestData.USER_1.getUsername());
		verify(dao);
		assertNotNull(actual);
		assertTrue(actual);
	}
	
	@Test
	public void emailExists() {
		expect(dao.emailExists(TestData.USER_1.getEmail())).andReturn(BigInteger.valueOf(TestData.USER_1.getId()));
		replay(dao);
		boolean actual = bo.emailExists(TestData.USER_1.getEmail());
		verify(dao);
		assertNotNull(actual);
		assertTrue(actual);
	}
}
