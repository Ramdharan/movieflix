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
	public Double getAverageRating(String id) {

		return ratingRepository.getAverageRatings(id);
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
		data.put("catalog_id", persisted_rating.getCatalog().getId());
		data.put("user_id", persisted_rating.getUser().getId());
		data.put("rating", persisted_rating.getRating());

		return data;
	}

}
