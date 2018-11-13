package com.poc.base.dao;

import java.io.Serializable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public abstract class BaseDaoImpl<T> implements BaseDao<T>, Serializable {
	@PersistenceContext
    protected EntityManager entityManager;

}
