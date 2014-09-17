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

import com.jeromesimmonds.phonebook.core.be.FindFilterType;
import com.jeromesimmonds.phonebook.core.be.FindParameters;
import com.jeromesimmonds.phonebook.core.be.PhoneNumber;

/**
 * @author Jerome Simmonds
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@Transactional
public class PhoneNumberDAOTest extends AbstractDAOTest {

	@Autowired
	private PhoneNumberDAO dao;
	
	@Test
	public void save() throws Exception {
		PhoneNumber pn = new PhoneNumber();
		pn.setContact(TestData.CONTACT_3);
		pn.setNumber("614 123 4444");
		pn.setType(PhoneNumber.TYPE_HOME);
		pn = dao.save(pn);
		assertNotNull(pn);
		assertTrue(pn.getId() > 0);
	}

	@Test
	public void delete() throws Exception {
		dao.delete(TestData.PHONE_NUMBER_1.getId());
		PhoneNumber pn = dao.findOne(TestData.PHONE_NUMBER_1.getId());
		assertNull(pn);
	}
	
	@Test
	public void search() throws Exception {
		FindParameters params = new FindParameters(1, 10)
			.with(FindFilterType.Contact, TestData.CONTACT_1);
		List<PhoneNumber> results = dao.findAll(new PhoneNumberFindSpecification(params));
		assertNotNull(results);
		assertEquals(2, results.size());
		
		params = new FindParameters(1, 10)
			.with(FindFilterType.Contact, TestData.CONTACT_2);
		results = dao.findAll(new PhoneNumberFindSpecification(params));
		assertNotNull(results);
		assertEquals(1, results.size());
	}
}
