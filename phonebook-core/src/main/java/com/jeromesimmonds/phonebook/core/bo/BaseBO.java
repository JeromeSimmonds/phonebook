package com.jeromesimmonds.phonebook.core.bo;

import java.io.Serializable;
import java.util.List;

import com.jeromesimmonds.phonebook.core.CoreException;
import com.jeromesimmonds.phonebook.core.be.Fetch;
import com.jeromesimmonds.phonebook.core.be.FindParameters;
import com.jeromesimmonds.phonebook.core.be.Findings;

/**
 * @author Jerome Simmonds
 *
 * @param <T> Entity type
 * @param <K> Entity id type
 */
public interface BaseBO<T, K extends Serializable> {

	public T load(K pKey) throws CoreException;
	
	public T load(K pKey, List<Fetch> pFetchGroup) throws CoreException;
	
	public T save(T pBE) throws CoreException;
	
	public void delete(T pBE) throws CoreException;

	public Findings<T> find(FindParameters pParams) throws CoreException;
	
	public List<T> save(Iterable<T> pBEs) throws CoreException;
}
