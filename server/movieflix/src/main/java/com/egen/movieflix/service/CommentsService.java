package com.egen.movieflix.service;

import java.util.List;
import java.util.Map;

import com.egen.movieflix.entity.Comments;
import com.egen.movieflix.utils.CommentsResponse;

public interface CommentsService {

	public Map<String, Object> insertNewComment(String comment, String catalogid);

	public List<CommentsResponse> getAllComments(String catalogid);

	public List<CommentsResponse> createCommentsResponse(List<Comments> comments);

}
