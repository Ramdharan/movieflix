package com.egen.movieflix.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.egen.movieflix.entity.Catalog;
import com.egen.movieflix.entity.Comments;
import com.egen.movieflix.entity.User;
import com.egen.movieflix.exception.CatalogDoestNotExists;
import com.egen.movieflix.repository.CommentsRepository;
import com.egen.movieflix.repository.UserRepository;
import com.egen.movieflix.security.JwtTokenUtil;
import com.egen.movieflix.security.JwtUser;
import com.egen.movieflix.utils.CommentsResponse;
import com.egen.movieflix.utils.Constants;

@Service
@Transactional
public class CommentsServiceImpl implements CommentsService {
	@Autowired
	public CommentsRepository commentsRepository;

	@Autowired
	public JwtTokenUtil jwtUtil;
	@Autowired
	UserRepository userRepository;

	@Override
	public Map<String, Object> insertNewComment(String comments, String catalogid) {

		JwtUser jwtuser = (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		User user = userRepository.getNewUser(jwtuser.getUsername());
		Comments comment = new Comments();

		Catalog catalog = commentsRepository.getCatalog(catalogid);
		if (catalog == null) {
			throw new CatalogDoestNotExists(Constants.INVALID_CATALOG_MSG);
		}

		comment.setCatalog(catalog);
		comment.setUser(user);
		comment.setComment(comments);

		Comments comment_result = commentsRepository.insertNewComment(comment);

		Map<String, Object> resultmap = new HashMap<String, Object>();
		resultmap.put("id", comment_result.getId());
		resultmap.put("user_id", comment_result.getUser().getId());
		resultmap.put("catalog_id", comment_result.getCatalog().getId());

		return resultmap;
	}

	@Override
	public List<CommentsResponse> getAllComments(String catalogid) {
		return createCommentsResponse(commentsRepository.getAllComments(catalogid));
	}

	@Override
	public List<CommentsResponse> createCommentsResponse(List<Comments> comments) {
		List<CommentsResponse> responseList = new ArrayList<CommentsResponse>();
		for (Comments comment : comments) {
			CommentsResponse response = new CommentsResponse();
			response.setComment(comment.getComment());
			response.setUsername(comment.getUser().getUsername());
			responseList.add(response);
		}
		return responseList;
	}
}
