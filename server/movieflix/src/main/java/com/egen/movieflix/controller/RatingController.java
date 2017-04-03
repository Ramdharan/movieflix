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
 * @author ramdharandonda
 *
 */
@RestController
@RequestMapping("/rating")

public class RatingController {
	@Autowired
	public RatingService ratingService;

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public Map<String, Object> insertNewRating(@RequestBody Object data) {
		// Spring will automatically convert JSON String to LinkedHashMap.
		// We can also create a POJO with all the fields in JSON.
		LinkedHashMap<String, Object> map = (LinkedHashMap<String, Object>) data;
		String catalogid = (String) map.get("catalogid");
		Double rating = (Double) map.get("rating");
		return ratingService.insertRating(catalogid, rating);
	}

	@RequestMapping(value = "/getavg", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public Double getAverageRatings(@RequestParam("catalogid") String id) {
		return ratingService.getAverageRating(id);
	}
}
