package com.egen.movieflix.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.egen.movieflix.entity.Catalog;
import com.egen.movieflix.entity.Ratings;
import com.egen.movieflix.entity.User;
import com.egen.movieflix.exception.CatalogDoestNotExists;
import com.egen.movieflix.repository.RatingRepository;
import com.egen.movieflix.repository.UserRepository;
import com.egen.movieflix.security.JwtTokenUtil;
import com.egen.movieflix.security.JwtUser;
import com.egen.movieflix.utils.Constants;

@Service
@Transactional
public class RatingServiceImpl implements RatingService {
	@Autowired
	public RatingRepository ratingRepository;

	@Autowired
	public JwtTokenUtil jwtUtil;
	@Autowired
	UserRepository userRepository;

	@Override
	public Map<String,Double> getAverageRating(String id) {

		Double result= ratingRepository.getAverageRatings(id);
		Map<String, Double> data = new HashMap<String, Double>();
		data.put("rating", result);
		return data;
	
	}

	@Override
	public Map<String, Object> insertRating(String catalogid, Double user_rating) {
		JwtUser jwtuser = (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		User user = userRepository.getNewUser(jwtuser.getUsername());

		Catalog catalog = ratingRepository.getCatalog(catalogid);
		if (catalog == null) {
			throw new CatalogDoestNotExists(Constants.INVALID_CATALOG_MSG);
		}

		Ratings rating = new Ratings();
		rating.setCatalog(catalog);
		rating.setRating(user_rating);
		rating.setUser(user);

		Ratings persisted_rating = ratingRepository.insertNewRating(rating);
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("id", persisted_rating.getId());
		System.out.println("returning id"+persisted_rating.getId());

		return data;
	}

	@Override
	public Map<String, Object> getUserRating(String catalogid) {
		User user = getUser();
		Ratings ratings = ratingRepository.getUserRating(user.getId(), catalogid);
		Map<String, Object> data = new HashMap<String, Object>();
		if (ratings != null) {
			data.put("ratingid", ratings.getId());
			data.put("rating", ratings.getRating());
			return data;
		} else {
			data.put("ratingid", null);
			data.put("rating", 0);
			return data;
		}
	}

	public User getUser() {
		JwtUser jwtuser = (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		User user = userRepository.getNewUser(jwtuser.getUsername());
		return user;
	}

	@Override
	public Map<String, Double> updateUserRatings(Double user_rating, String ratingid) {
		Map<String, Double> data = new HashMap<String, Double>();
		Double result=ratingRepository.updateUserRating(user_rating, ratingid);
		data.put("rating", result);
		return data;
	}
}
