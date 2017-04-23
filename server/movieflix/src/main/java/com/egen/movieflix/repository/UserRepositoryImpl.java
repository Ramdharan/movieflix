package com.egen.movieflix.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.egen.movieflix.entity.User;

@Repository
public class UserRepositoryImpl implements UserRepository {
	@PersistenceContext
	EntityManager entityManager;

	@Override
	public boolean isUserExists(String username) {
		TypedQuery<User> query = entityManager.createNamedQuery("User.findByUserName", User.class);
		query.setParameter("username", username);
		return query.getResultList().size() > 0 ? true : false;
	}

	@Override
	public User getNewUser(String username) {
		System.out.println(username);
		TypedQuery<User> user = entityManager.createNamedQuery("User.findByUserName", User.class);
		user.setParameter("username", username);
		User data = user.getSingleResult();
		System.out.println(data.toString());

		return data;
	}

	@Override
	public User createUser(User user) {
		entityManager.persist(user);
		return user;
	}

}
