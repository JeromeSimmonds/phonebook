package com.jeromesimmonds.phonebook.core.bo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.jeromesimmonds.phonebook.core.CoreException;
import com.jeromesimmonds.phonebook.core.be.Fetch;
import com.jeromesimmonds.phonebook.core.be.FindParameters;
import com.jeromesimmonds.phonebook.core.be.Findings;
import com.jeromesimmonds.phonebook.core.dao.BaseDAO;
import com.jeromesimmonds.phonebook.core.dao.FindSpecification;


/**
 * @author Jerome Simmonds
 *
 * @param <T> Entity type
 * @param <K> Entity id type
 * @param <S> Data Access Object type
 */
public abstract class AbstractBO<T, K extends Serializable, S extends BaseDAO<T, K>> implements BaseBO<T, K> {
	
	private S dao;
	    
    public T load(K key) throws CoreException {
    	return dao.findOne(key);
    }
    
    public T load(K key, List<Fetch> fetchGroup) throws CoreException {
		T oResult = dao.findOne(key);
		applyFetches(oResult, fetchGroup);
		return oResult;
	}
    
    public T save(T entity) throws CoreException {
    	if (entity == null)
    		throw new CoreException("BE cannot be null.");
    	return dao.save(entity);
    }
    
    public void delete(T entity) throws CoreException {
    	if (entity == null)
    		throw new CoreException("BE cannot be null");
    	dao.delete(entity);
    }
    
    @Override
    public List<T> save(Iterable<T> entities) throws CoreException {
    	if (entities == null)
    		throw new CoreException("BE list cannot be null.");
    	return dao.save(entities);
    }

    public Findings<T> find(FindParameters params) throws CoreException {
    	if (params == null)
    		throw new CoreException("params can not be null.");

    	Findings<T> result = new Findings<T>();
    	Specification<T> spec = newSpecification(params);
    	Page<T> page = null;
    	if (spec != null) {
	    	Pageable oPageable = null;
	    	if (spec instanceof FindSpecification) {
	    		FindSpecification oFindSpec = (FindSpecification) spec;
	    		oPageable = oFindSpec.toPageable();
	    	}
	    	// if (pParams == null) oReturn.setResults(mDAO.readAll());
	    	page = dao.findAll(spec, oPageable);
	    	
    	} else {
    		int oPageSize = Math.abs(params.getTo() - params.getFrom()) + 1;
        	int oPageNumber = params.getFrom() / oPageSize;
        	page = dao.findAll(new PageRequest(oPageNumber, oPageSize));
    	}
    	result.setResults(applyFetches(page.getContent(), params.getFetchGroup()));
    	result.setTotalAvailable((int) page.getTotalElements());

    	return result;
    }
    
    protected List<T> applyFetches(List<T> entities, List<Fetch> fetchGroup) {
    	if (fetchGroup == null || fetchGroup.isEmpty()) return entities;
		for (T e : entities) {
			applyFetches(e, fetchGroup);
		}
		return entities;
	}
    
    /**
	 * Override this to enable fetches.
	 * @param pResult
	 * @param pFetchGroup
	 */
	protected void applyFetches(T result, List<Fetch> fetchGroup) throws CoreException {
		if (fetchGroup == null) return;
		for (Fetch f : fetchGroup) {
			applyFetch(result, f);
		}
	}
	
	/**
	 * Override this to provide custom fetch.
	 */
	protected void applyFetch(T result, Fetch fetch) throws CoreException {
	}
    
    protected abstract Specification<T> newSpecification(FindParameters findParameters) throws CoreException;
    
    public S getDAO() {
		return dao;
	}

	public void setDAO(S dao) {
		this.dao = dao;
	}
}
