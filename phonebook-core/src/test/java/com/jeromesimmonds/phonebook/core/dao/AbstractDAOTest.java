package com.jeromesimmonds.phonebook.core.dao;

import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.transaction.BeforeTransaction;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Jerome Simmonds
 *
 */
@Transactional
public abstract class AbstractDAOTest {

	@Autowired
	protected DBUtils DBUtils;
	
	@Before
	@BeforeTransaction
	public void daoSetup() throws Exception {
		DBUtils.validateDatabase();
	}
}
