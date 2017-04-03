package com.egen.movieflix.service;

import java.util.Map;

public interface RatingService {
	public Map<String, Object> insertRating(String catalogid, Double rating);

	public Double getAverageRating(String id);

}
