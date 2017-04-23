package com.egen.movieflix.service;

import java.util.Map;

public interface RatingService {
	public Map<String, Object> insertRating(String catalogid, Double rating);

	public Map<String,Double> getAverageRating(String id);
	
	public Map<String,Object> getUserRating(String catalogid);
	

	public Map<String,Double> updateUserRatings(Double user_rating, String ratingid);

}
