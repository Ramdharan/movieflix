package com.egen.movieflix.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.egen.movieflix.service.RatingService;

/**
 * Handles mapping request related to Ratings
 * 
 *
 *
 */
@RestController
@RequestMapping("/rating")

public class RatingController {
	@Autowired
	public RatingService ratingService;

	@RequestMapping(value = "/insert", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public Map<String, Object> insertNewRating(@RequestBody Object data) {
		// Spring will automatically convert JSON String to LinkedHashMap.
		// We can also create a POJO with all the fields in JSON.

		System.out.println("Insert new rating....");
		LinkedHashMap<String, Object> map = (LinkedHashMap<String, Object>) data;
		String catalogid = (String) map.get("catalogid");
		double rating = getRating(map.get("rating"));

		// double rating = (double) map.get("rating");
		return ratingService.insertRating(catalogid, rating);
	}

	@RequestMapping(value = "/getavg", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public Map<String, Double> getAverageRatings(@RequestParam("catalogid") String id) {

		return ratingService.getAverageRating(id);
	}

	@RequestMapping(value = "/getuserrating", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public Map<String, Object> getUserRating(@RequestParam("catalogid") String catalogid) {
		return ratingService.getUserRating(catalogid);
	}

	@RequestMapping(value = "/updaterating", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public Map<String, Double> updateUserRating(@RequestBody Object data) {
		LinkedHashMap<String, Object> map = (LinkedHashMap<String, Object>) data;
		String ratingid = (String) map.get("ratingid");
		double rating = getRating(map.get("rating"));

		return ratingService.updateUserRatings(rating, ratingid);
	}

	/**
	 * Converts user rating to double
	 * 
	 * @param ratingObj
	 * @return
	 */

	public double getRating(Object ratingObj) {
		double rating;

		try {
			int rated = (int) ratingObj;
			rating = (double) rated;
			return rating;
		} catch (ClassCastException ex) {
			rating = (double) ratingObj;
			return rating;
		}
	}
}
