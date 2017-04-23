package com.egen.movieflix.repository;

import com.egen.movieflix.entity.Catalog;
import com.egen.movieflix.entity.Ratings;

public interface RatingRepository {

	public Ratings insertNewRating(Ratings rating);

	public Double getAverageRatings(String id);

public Ratings getUserRating(String userid,String catalogid);

public Catalog getCatalog(String catalogid);	

public Double  updateUserRating(Double rating,String ratingid);

}
