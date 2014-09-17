package com.jeromesimmonds.phonebook.core.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.jeromesimmonds.phonebook.core.be.Contact;
import com.jeromesimmonds.phonebook.core.be.FindFilterType;
import com.jeromesimmonds.phonebook.core.be.FindParameters;

/**
 * @author Jerome Simmonds
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@Transactional
public class ContactDAOTest extends AbstractDAOTest {

	@Autowired
	private ContactDAO dao;
	
	@Test
	public void save() throws Exception {
		Contact c = new Contact();
		c.setFirstName("Bob");
		c.setLastName("Chirac");
		c.setUser(TestData.USER_2);
		c = dao.save(c);
		assertNotNull(c);
		assertTrue(c.getId() > 0);
	}
	
	@Test
	public void load() throws Exception {
		Contact c = dao.findOne(TestData.CONTACT_2.getId());
		assertNotNull(c);
		assertEquals(TestData.CONTACT_2.getFirstName(), c.getFirstName());
		assertEquals(TestData.CONTACT_2.getLastName(), c.getLastName());
		assertEquals(TestData.CONTACT_2.getUser().getId(), c.getUser().getId());
	}

	@Test
	public void delete() throws Exception {
		dao.delete(TestData.CONTACT_2.getId());
		Contact c = dao.findOne(TestData.CONTACT_2.getId());
		assertNull(c);
	}
	
	@Test
	public void search() throws Exception {
		FindParameters params = new FindParameters(1, 10)
			.with(FindFilterType.User, TestData.USER_1);
		List<Contact> results = dao.findAll(new ContactFindSpecification(params));
		assertNotNull(results);
		assertEquals(2, results.size());
		
		params = new FindParameters(1, 10)
			.with(FindFilterType.User, TestData.USER_2);
		results = dao.findAll(new ContactFindSpecification(params));
		assertNotNull(results);
		assertEquals(1, results.size());
		assertEquals(TestData.CONTACT_3.getId(), results.get(0).getId());
		
		params = new FindParameters(1, 10)
			.with(FindFilterType.FirstName, TestData.CONTACT_3.getFirstName());
		results = dao.findAll(new ContactFindSpecification(params));
		assertNotNull(results);
		assertEquals(1, results.size());
		assertEquals(TestData.CONTACT_3.getId(), results.get(0).getId());
		
		params = new FindParameters(1, 10)
			.with(FindFilterType.LastName, TestData.CONTACT_2.getLastName());
		results = dao.findAll(new ContactFindSpecification(params));
		assertNotNull(results);
		assertEquals(1, results.size());
		assertEquals(TestData.CONTACT_2.getId(), results.get(0).getId());
		
		params = new FindParameters(1, 10)
			.with(FindFilterType.LastName, "fsdfqsdf");
		results = dao.findAll(new ContactFindSpecification(params));
		assertNotNull(results);
		assertEquals(0, results.size());
	}
}
