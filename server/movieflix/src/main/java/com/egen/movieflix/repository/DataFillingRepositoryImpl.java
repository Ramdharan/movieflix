package com.egen.movieflix.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.egen.movieflix.entity.Authority;
import com.egen.movieflix.entity.Catalog;
import com.egen.movieflix.entity.User;

@Repository
public class DataFillingRepositoryImpl implements DataFillingRepository {
	@PersistenceContext
	EntityManager entityManager;

	/*
	 * Persist Catalog data
	 * 
	 */
	@Override
	public void fillIntialData(List<Catalog> dataList) {

		for (Catalog catalog : dataList)
			entityManager.persist(catalog);

	}

	@Override
	public void insertNewUser(User user) {
		entityManager.persist(user);
	}

	@Override
	public void saveAuthority(Authority auth) {
		entityManager.persist(auth);

	}

}
