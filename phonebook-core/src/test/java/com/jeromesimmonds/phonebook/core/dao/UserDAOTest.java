package com.jeromesimmonds.phonebook.core.dao;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import java.math.BigInteger;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.jeromesimmonds.phonebook.core.be.User;

/**
 * @author Jerome Simmonds
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@Transactional
public class UserDAOTest extends AbstractDAOTest {

	@Autowired
	private UserDAO dao;
	
	@Test
	public void save() throws Exception {
		User u = new User();
		u.setUsername("Username3");
		u.setEmail("user3@site.com");
		u.setPassword("pwd");
		u = dao.save(u);
		assertNotNull(u);
		assertTrue(u.getId() > 0);
	}
	
	@Test
	public void load() throws Exception {
		User u = dao.findOne(TestData.USER_1.getId());
		assertNotNull(u);
		assertEquals(TestData.USER_1.getUsername(), u.getUsername());
	}

	@Test
	public void findByUsername() throws Exception {
		User u = dao.findByUsername(TestData.USER_1.getUsername());
		assertNotNull(u);
		assertEquals(TestData.USER_1.getId(), u.getId());
		
		// Shouldn't exist
		u = dao.findByUsername("fjsksqflqfjk");
		assertNull(u);
	}

	@Test
	public void findByEmail() throws Exception {
		User u = dao.findByEmail(TestData.USER_1.getEmail());
		assertNotNull(u);
		assertEquals(TestData.USER_1.getId(), u.getId());
		
		// Shouldn't exist
		u = dao.findByEmail("fjsksqflqfjk");
		assertNull(u);
	}
	
	@Test
	public void exists() throws Exception {
		BigInteger r = dao.usernameExists(TestData.USER_1.getUsername());
		assertEquals(r, BigInteger.valueOf(1l));
		r = dao.emailExists(TestData.USER_1.getEmail());
		assertEquals(r, BigInteger.valueOf(1l));
		
		// Shouldn't exist
		r = dao.usernameExists("fdsfdsd");
		assertNull(r);
		r = dao.emailExists("fdsfdsd");
		assertNull(r);
	}
}
