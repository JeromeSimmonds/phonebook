package com.jeromesimmonds.phonebook.core.dao;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


/**
 * @author Jerome Simmonds
 *
 * @param <T> Entiy type
 * @param <K> Entity id type
 */
public interface BaseDAO<T, K extends Serializable> extends JpaRepository<T, K>, JpaSpecificationExecutor<T> {

}
