package com.egen.movieflix.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.egen.movieflix.service.CommentsService;
import com.egen.movieflix.utils.CommentsResponse;
import com.egen.movieflix.utils.Constants;

/**
 * Handles mapping requests related to Comments
 * 
 * @author ramdharandonda
 *
 */
@RestController
@RequestMapping("/comments")
public class CommentsController {
	@Autowired
	public CommentsService commentsService;

	@RequestMapping(value = "/post", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public Map<String, Object> insertNewComment(@RequestBody Object obj,
			@RequestHeader(Constants.TOKEN_HEADER) String token) {

		LinkedHashMap<String, String> data = (LinkedHashMap<String, String>) obj;

		return commentsService.insertNewComment(data.get("comments"), data.get("catalogid"));
	}

	@RequestMapping(value = "/getall", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public List<CommentsResponse> getAll(@RequestParam("catalogid") String catalogid,
			@RequestHeader(Constants.TOKEN_HEADER) String token) {
		return commentsService.getAllComments(catalogid);
	}
}
