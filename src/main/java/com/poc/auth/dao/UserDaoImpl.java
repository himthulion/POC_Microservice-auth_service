package com.poc.auth.dao;

import javax.persistence.TypedQuery;

import org.hibernate.Session;

import com.poc.auth.entity.User;

import com.poc.base.dao.BaseDaoImpl;

public class UserDaoImpl extends BaseDaoImpl<User> implements UserDaoCustom {

	@Override
	public int updateUserNameById(Long id, String newName) throws Exception {
		Session session = entityManager.unwrap(Session.class);
		String hql = "update User u ";
		hql += "set u.name = :nameParam ";
		hql += "where u.id = :idParam";
		TypedQuery<User> query = session.createQuery(hql);
		query.setParameter("nameParam", newName);
		query.setParameter("idParam", id);
		int result = query.executeUpdate();
		return result;
	}

}
