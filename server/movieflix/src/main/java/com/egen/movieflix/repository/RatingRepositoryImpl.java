package com.egen.movieflix.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.egen.movieflix.entity.Catalog;
import com.egen.movieflix.entity.Ratings;

@Repository
public class RatingRepositoryImpl implements RatingRepository {
	@PersistenceContext
	public EntityManager entityManager;

	@Override
	public Ratings insertNewRating(Ratings rating) {
		entityManager.persist(rating);
		return rating;
	}

	@Override
	public Double getAverageRatings(String id) {

		TypedQuery<Object> query = entityManager.createNamedQuery("Ratings.getAvgRating", Object.class);
		query.setParameter("catalog_id", id);
		return (Double) query.getSingleResult();
	}

	@Override
	public Catalog getCatalog(String id) {
		Catalog catalog = entityManager.find(Catalog.class, id);
		return catalog;
	}

}
