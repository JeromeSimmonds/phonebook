package com.jeromesimmonds.phonebook.core.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.jeromesimmonds.phonebook.core.be.UserToken;

/**
 * @author Jerome Simmonds
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@Transactional
public class UserTokenDAOTest extends AbstractDAOTest {

	@Autowired
	private UserTokenDAO dao;
	
	@Test
	public void save() throws Exception {
		UserToken t = new UserToken();
		t.setToken("token");
		t.setUser(TestData.USER_1);
		t.setTypeId(UserToken.TYPE_FORGOTPASSWORD);
		t = dao.save(t);
		assertNotNull(t);
	}
	
	@Test
	public void load() throws Exception {
		UserToken t = dao.findOne(TestData.USERTOKEN_1.getToken());
		assertNotNull(t);
		assertEquals(TestData.USERTOKEN_1.getUser().getId(), t.getUser().getId());
		assertEquals(TestData.USERTOKEN_1.getTypeId(), UserToken.TYPE_EMAILCONFIRMATION);
	}
}
