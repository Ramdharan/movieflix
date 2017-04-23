package com.egen.movieflix.repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
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
		try{
		Double avgRating=	 (Double) query.getSingleResult();
		return avgRating;
		}catch(NoResultException ex){
			return 0.0;
			}
	}

	@Override
	public Catalog getCatalog(String id) {
		Catalog catalog = entityManager.find(Catalog.class, id);
		return catalog;
	}

	@Override
	public Ratings getUserRating(String userid, String catalogid) {
		TypedQuery<Object> query=entityManager.createNamedQuery("Ratings.getUserRatings",Object.class);
		query.setParameter("catalog_id",catalogid);
		query.setParameter("user_id",userid);
		try{
		 return (Ratings) query.getSingleResult();
		}catch(NoResultException noresult){
		return null;	
		}
	}

	@Override
	public Double updateUserRating(Double rating, String ratingid) {
	Ratings userRating=entityManager.find(Ratings.class,ratingid);
	userRating.setRating(rating);
	entityManager.persist(userRating);
	return rating;
	}

}
